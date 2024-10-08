package com.hyperos.cloudshellconfig.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2024/10/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@Slf4j
public class Config {

    private ResourceLoader resourceLoader;

    public Config(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static String cluster;

    public static String namespace;

    public static String userSystemPath;

    @Value(value = "${k8s-config.kubeConfigPath}")
    public void setCluster(String cluster) throws IOException {
        if (cluster.startsWith("classpath:")) {
            this.cluster = resourceLoader.getResource(cluster).getFile().getAbsolutePath();
        } else {
            this.cluster = System.getProperty("user.dir") + cluster;
        }
        log.info("setKubeConfigPath--> " + this.cluster);

    }

    @Value(value = "${k8s-config.namespace}")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Value(value = "${k8s-config.userSystemPath}")
    public void setUserSystemPath(String userSystemPath) {
        this.userSystemPath = userSystemPath;
    }

}
