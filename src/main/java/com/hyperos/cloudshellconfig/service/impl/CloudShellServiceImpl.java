package com.hyperos.cloudshellconfig.service.impl;

import com.hyperos.cloudshellconfig.config.Config;
import com.hyperos.cloudshellconfig.exception.UserSystemException;
import com.hyperos.cloudshellconfig.response.ResponseCode;
import com.hyperos.cloudshellconfig.service.CloudShellService;
import com.hyperos.cloudshellconfig.utils.cloudtty.*;
import com.hyperos.cloudshellconfig.utils.k8s.ClusterClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
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
    @Autowired
    private Config config;


    /**
     * 创建cloud shell
     *
     * @param serviceName serviceName
     * @return cloud shell 地址
     */
    @Override
    public String createCloudShell(String serviceName) {

        String kubeConfig = createKubeConfig(Config.namespace, serviceName);

        createConfigSecret(kubeConfig, serviceName);
        //创建cloud shell
        GenericKubernetesApi<V1alpha1CloudShell, V1alpha1CloudShellList> cloudShellClient = clusterClient.getCloudShellClient();

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
        spec.setImage("121.40.102.76:30080/pml_user_system/cloudshell:v0.7.5");


        V1alpha1CloudShellSpecIngressConfig ingressConfig = new V1alpha1CloudShellSpecIngressConfig();
        ingressConfig.setIngressName("cloudshell-ingress" + serviceName);
        ingressConfig.setNamespace(Config.namespace);
        ingressConfig.setIngressClassName("nginx");
        spec.setIngressConfig(ingressConfig);

        cloudShellClient.create(Config.namespace, v1alpha1CloudShell, null);
        return "";
    }


    public void createConfigSecret(String k8sConfig, String serviceAccountName) {

        ApiClient apiClient = clusterClient.getUserSystemClient();

        CoreV1Api coreV1Api = new CoreV1Api(apiClient);

        byte[] kubeConfigBase64 = Base64.getEncoder().encode(k8sConfig.getBytes());


        try {
            // 创建 Secret
            V1Secret secret = new V1Secret()
                    .metadata(new V1ObjectMeta().name(serviceAccountName).namespace("cloud-shell"))
                    .data(Map.of("config", kubeConfigBase64)) // 将 kubeConfig 内容作为 Secret 数据
                    .type("Opaque");

            coreV1Api.createNamespacedSecret("cloud-shell", secret, null, null, null, null);

            log.info("Secret created successfully with kubeConfig.");
        } catch (ApiException e) {
            log.error("create config secret error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell90003);
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
            throw new UserSystemException(ResponseCode.CloudShell90001);
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
                        throw new UserSystemException(ResponseCode.CloudShell90000);
                    }
                    byte[] tokenByte = secret.getData().get("token");
                    token = new String(tokenByte);
                }
            } else {
                log.error("secrets is null from core1Cluster1.");
                throw new UserSystemException(ResponseCode.CloudShell90000);
            }
        } catch (ApiException e) {
            log.error("get core1Cluster1 resource error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell90002);
        }


        try {
            FileReader fr = new FileReader(Config.cluster);
            KubeConfig kubeConfig = KubeConfig.loadKubeConfig(fr);

            String server = kubeConfig.getServer();
            String certificateAuthorityData = kubeConfig.getCertificateAuthorityData();

            // 创建一个包含多个集群、用户和上下文的 kubeconfig 内容
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
            return yaml.dump(kubeConfig);

        } catch (Exception e) {
            log.error("get core1Cluster1 resource error. {}.", e.getMessage());
            return null;
        }


//        //生成 kubeConfig
//        String kubeConfig = String.format(
//                "apiVersion: v1\n" +
//                        "clusters:\n" +
//                        "- cluster:\n" +
//                        "    server: https://47.96.253.194:6443\n" +
//                        "    certificate-authority-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM1ekNDQWMrZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRJek1Ea3lOakV3TVRFd05Gb1hEVE16TURreU16RXdNVEV3TkZvd0ZURVRNQkVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBS1g2CjVYNU01Ym5CMDM2NnhCZDNpTjNITmhKVHRLNDkwOWEwWGgrWkFUcnZ6dkEzaHR2M21MVUM1RzlQbkNPY3hhQUEKZ09JZWZLL0hBZ3lKdTZSNEo3YVdSNE1lQW1tbTUreUtyRWJyNFF4VTVmeFBRVXdwSDJwWmRIZGpqNTlGVE9VNgpEb2NEb3Jza213RjVKckp5bXdQWFFwTUU5L0haZnNlbFd1d3BKTTZna2tBVG9xMHlhbWxhMGEzQXZKUFBuVkRYCmJlVERTbGFmRHUyM3FmV0RKSmRWRk9xclJHUDVVbUhVRStFUitiQW9JSERNSk1RbjJsdEZWckk2TnpTNDlBazMKTTVKd2JxVnRNRjVtSysrZHJWVEtsKzc4blBIcVR3czVPV2dlL1ZLZHJpaFozM21jU25qM09QY0NwajU1VWN3agp5aGRGbHo5ZVRtcEd1TTlIOFUwQ0F3RUFBYU5DTUVBd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU1CQWY4d0hRWURWUjBPQkJZRUZGdTlXTmlnSis2VWtHUGdMSnovQjk0c0lZSzBNQTBHQ1NxR1NJYjMKRFFFQkN3VUFBNElCQVFCWG9vSlI3Tkpxa2NZQ3E2R3IrN1F1dnYzbFhzbTJmbzVieXJ6djRzZmVkQ0lGdXR0Ywp1aGpRanJaWVc1U1lLOVRKc0RIdEpOM05VTXRxSHp0MjBKaGpqREVhWWlUbTFJbkdwUmtsZzhoZ2d6MEVDMjQwCktYc1NmaFdYa2dhWm1xSUtuS3dtYUNqdjh1emZpK1Y3eXJzYXFEdU5lc3cyK3Y2dEIvZFpMS0RldkwyMXJHWTUKY0NVcDRPZmtqdWxseHZKOVJSZlM2SGY3SEx1Y2JjTjFwdGpUK0NRTlpZckFwVjc1aEltM0tEaW1yYXdlMXZEVAorVlljWmJ4VmJaZGVkWEg5WVltdUtYVGVHNlRxODNsTXlYOHhzUGJqUGdBbzJqdTNBdVZXV2VqbE1LZ0xhTm45Cm5GMkY5VXdtMndMMFVKdDRQdzJRNU4xanFqek1lbjJ6dmJ3VAotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==\n" +
//                        "  name: example-cluster\n" +
//                        "contexts:\n" +
//                        "- context:\n" +
//                        "    cluster: example-cluster\n" +
//                        "    namespace: %s\n" +
//                        "    user: example-sa\n" +
//                        "  name: example-context\n" +
//                        "current-context: example-context\n" +
//                        "users:\n" +
//                        "- name: example-sa\n" +
//                        "  user:\n" +
//                        "    token: %s\n", namespace, token);
//        return kubeConfig;
    }


}
