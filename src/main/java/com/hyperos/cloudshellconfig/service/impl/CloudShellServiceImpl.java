package com.hyperos.cloudshellconfig.service.impl;

import com.hyperos.cloudshellconfig.config.Config;
import com.hyperos.cloudshellconfig.exception.UserSystemException;
import com.hyperos.cloudshellconfig.response.ResponseCode;
import com.hyperos.cloudshellconfig.service.CloudShellService;
import com.hyperos.cloudshellconfig.utils.Constants;
import com.hyperos.cloudshellconfig.utils.cloudtty.*;
import com.hyperos.cloudshellconfig.utils.k8s.ClusterClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import io.kubernetes.client.util.generic.options.CreateOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2024/10/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
@Slf4j
public class CloudShellServiceImpl implements CloudShellService {

    @Autowired
    private ClusterClient clusterClient;

    /**
     * 创建cloud shell
     *
     * @param teamName teamName
     * @return cloud shell 地址
     */
    @Override
    public String createCloudShell(String teamName) {

        String namespace = Config.namespace;
        String serviceName = teamName;

        GenericKubernetesApi<V1alpha1CloudShell, V1alpha1CloudShellList> cloudShellClient = clusterClient.getCloudShellClient();

        // 查询CloudShell
        V1alpha1CloudShell v1alpha1CloudShellFromEnv = cloudShellClient.get(namespace, serviceName).getObject();

        if (v1alpha1CloudShellFromEnv != null) {
            log.warn("Cloud Shell {} already exists.", serviceName);
            return Constants.CLOUD_PRE_PATH + "/" + serviceName;
        }


        String kubeConfig = createKubeConfig(namespace, serviceName);

        createConfigSecret(namespace, kubeConfig, serviceName);
        //创建cloud shell
        V1alpha1CloudShell v1alpha1CloudShell = new V1alpha1CloudShell();

        v1alpha1CloudShell.setApiVersion("cloudshell.cloudtty.io/v1alpha1");
        v1alpha1CloudShell.setKind("CloudShell");
        v1alpha1CloudShell.setMetadata(new V1ObjectMeta().name(serviceName));
        V1alpha1CloudShellSpec spec = new V1alpha1CloudShellSpec();
        v1alpha1CloudShell.setSpec(spec);

        V1alpha1CloudShellSpecSecretRef secretRef = new V1alpha1CloudShellSpecSecretRef();
        spec.setSecretRef(secretRef);
        secretRef.setName(serviceName);

        spec.setCommandAction("bash");
        spec.setOnce(false);
        spec.setExposureMode(V1alpha1CloudShellSpec.ExposureModeEnum.INGRESS);
        spec.setImage(Config.cloudShellImage);


        V1alpha1CloudShellSpecIngressConfig ingressConfig = new V1alpha1CloudShellSpecIngressConfig();
        ingressConfig.setIngressName("cloudshell-ingress" + serviceName);
        ingressConfig.setNamespace(namespace);
        ingressConfig.setIngressClassName("nginx");
        spec.setIngressConfig(ingressConfig);

        cloudShellClient.create(namespace, v1alpha1CloudShell, new CreateOptions());

        return Constants.CLOUD_PRE_PATH + serviceName;
    }


    public void createConfigSecret(String namespace, String k8sConfig, String serviceAccountName) {

        ApiClient apiClient = clusterClient.getUserSystemClient();

        CoreV1Api coreV1Api = new CoreV1Api(apiClient);

        try {
            // 创建 Secret
            V1Secret secret = new V1Secret()
                    .metadata(new V1ObjectMeta().name(serviceAccountName).namespace(namespace))
                    .data(Map.of("config", k8sConfig.getBytes())) // 将 kubeConfig 内容作为 Secret 数据
                    .type("Opaque");

            coreV1Api.createNamespacedSecret(namespace, secret, null, null, null, null);

            log.info("Secret created successfully with kubeConfig.");
        } catch (ApiException e) {
            log.error("create config secret error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell100004);
        }
    }


    public String createKubeConfig(String namespace, String serviceAccountName) {

        ApiClient apiClient = clusterClient.getClusterClient();
        CoreV1Api coreV1Api = new CoreV1Api(apiClient);
        try {
            // 1、创建ServiceAccount
            V1ServiceAccount serviceAccount = new V1ServiceAccount()
                    .metadata(new V1ObjectMeta().name(serviceAccountName).namespace(namespace));
            coreV1Api.createNamespacedServiceAccount(namespace, serviceAccount, null, null, null, null);

            // 2、创建 Role
            RbacAuthorizationV1Api rbacApi = new RbacAuthorizationV1Api();
            V1Role role = new V1Role()
                    .metadata(new V1ObjectMeta().name(serviceAccountName + "-role").namespace(namespace))
                    .rules(Collections.singletonList(
                            new V1PolicyRule()
                                    .verbs(Arrays.asList("get", "watch", "list"))
                                    .apiGroups(List.of(""))
                                    .resources(List.of("pods"))
                    ));
            rbacApi.createNamespacedRole(namespace, role, null, null, null, null);

            // 3、创建 RoleBinding
            V1RoleBinding roleBinding = new V1RoleBinding()
                    .metadata(new V1ObjectMeta().name(serviceAccountName + "-rb").namespace(namespace))
                    .subjects(Collections.singletonList(new V1Subject()
                            .kind("ServiceAccount")
                            .name(serviceAccountName)
                            .namespace(namespace)))
                    .roleRef(new V1RoleRef()
                            .kind("Role")
                            .name(serviceAccountName + "-role")
                            .apiGroup("rbac.authorization.k8s.io"));
            rbacApi.createNamespacedRoleBinding(namespace, roleBinding, null, null, null, null);
        } catch (ApiException e) {
            log.error("create core1Cluster1 resource error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell100002);
        }


        // 4、获取token
        String token = "";

        try {
            // 获取 ServiceAccount
            V1ServiceAccount sa = coreV1Api.readNamespacedServiceAccount(serviceAccountName, namespace, null);

            // 获取关联的 Secret
            List<V1ObjectReference> secrets = sa.getSecrets();
            if (secrets != null) {
                for (V1ObjectReference secretMeta : secrets) {
                    String secretName = secretMeta.getName();
                    V1Secret secret = coreV1Api.readNamespacedSecret(secretName, namespace, null);
                    if (secret == null || secret.getData() == null) {
                        log.error("get secret is null from core1Cluster1. secret is {}.", secretName);
                        throw new UserSystemException(ResponseCode.CloudShell100001);
                    }
                    byte[] tokenByte = secret.getData().get("token");
                    token = new String(tokenByte);
                }
            } else {
                log.error("secrets is null from core1Cluster1.");
                throw new UserSystemException(ResponseCode.CloudShell100001);
            }
        } catch (ApiException e) {
            log.error("get core1Cluster1 resource error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell100003);
        }


        try {
            FileReader fr = new FileReader(Config.cluster);
            KubeConfig kubeConfig = KubeConfig.loadKubeConfig(fr);

            String server = kubeConfig.getServer();
            String certificateAuthorityData = kubeConfig.getCertificateAuthorityData();

            // 创建一个包含多个集群、用户和上下文的 config 内容
            Map<String, Object> kubeConfigMap = new HashMap<>();

            // apiVersion配置
            kubeConfigMap.put("apiVersion", "v1");
            kubeConfigMap.put("kind", "Config");
            kubeConfigMap.put("preferences", new HashMap<>());

            List<Map<String, Object>> clusters = new ArrayList<>();
            Map<String, Object> cluster = new HashMap<>();
            cluster.put("cluster", new HashMap<String, Object>() {{
                put("certificate-authority-data", certificateAuthorityData);
                put("server", server);
            }});
            cluster.put("name", "kubernetes");
            clusters.add(cluster);
            kubeConfigMap.put("clusters", clusters);

            List<Map<String, Object>> contexts = new ArrayList<>();
            Map<String, Object> context = new HashMap<>();
            context.put("context", new HashMap<String, Object>() {{
                put("cluster", "kubernetes");
                put("namespace", namespace);
                put("user", serviceAccountName);
            }});
            context.put("name", serviceAccountName + "@kubernetes");
            contexts.add(context);
            kubeConfigMap.put("contexts", contexts);

            kubeConfigMap.put("current-context", serviceAccountName + "@kubernetes");

            List<Map<String, Object>> users = new ArrayList<>();
            Map<String, Object> user = new HashMap<>();
            user.put("name", serviceAccountName);
            String finalToken = token;
            user.put("user", new HashMap<String, Object>() {{
                put("token", finalToken);
            }});
            users.add(user);
            kubeConfigMap.put("users", users);


            // 将数据结构转换为 YAML 字符串
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);

            return yaml.dump(kubeConfigMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserSystemException(ResponseCode.CloudShell100001);
        }
    }
}
