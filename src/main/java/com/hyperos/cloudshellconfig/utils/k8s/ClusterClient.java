package com.hyperos.cloudshellconfig.utils.k8s;

import com.hyperos.cloudshellconfig.config.Config;
import com.hyperos.cloudshellconfig.response.ResponseCode;
import com.hyperos.cloudshellconfig.utils.cloudtty.V1alpha1CloudShell;
import com.hyperos.cloudshellconfig.utils.cloudtty.V1alpha1CloudShellList;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.hyperos.cloudshellconfig.exception.UserSystemException;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2024/10/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
@Slf4j
public class ClusterClient {

    public ApiClient getClusterClient() {
        try {
            ApiClient apiClient =
                    ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.cluster))).build();

            // set the global default api-client to the in-cluster one from above
            Configuration.setDefaultApiClient(apiClient);

            return apiClient;
        } catch (IOException e) {
            log.error("create core1Cluster1 client error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell100005);
        }

    }

    public ApiClient getUserSystemClient() {
        try {
            ApiClient apiClient =
                    ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.userSystemPath))).build();

            // set the global default api-client to the in-cluster one from above
            Configuration.setDefaultApiClient(apiClient);

            return apiClient;
        } catch (IOException e) {
            log.error("create core1Cluster1 client error. {}.", e.getMessage());
            throw new UserSystemException(ResponseCode.CloudShell100005);
        }

    }

    public GenericKubernetesApi<V1alpha1CloudShell, V1alpha1CloudShellList> getCloudShellClient()  {
        return new GenericKubernetesApi<>(
                V1alpha1CloudShell.class, V1alpha1CloudShellList.class,
                "cloudshell.cloudtty.io",
                "v1alpha1",
                "cloudshells",
                getUserSystemClient());
    }
}
