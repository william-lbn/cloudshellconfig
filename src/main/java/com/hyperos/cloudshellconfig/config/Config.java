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

    public static String cloudShellImage;

    @Value(value = "${k8s-config.kubeConfigPath}")
    public void setCluster(String cluster) throws IOException {
        if (cluster.startsWith("classpath:")) {
            this.cluster = resourceLoader.getResource(cluster).getFile().getAbsolutePath();
        } else {
            this.cluster = System.getProperty("user.dir") + cluster;
        }
        log.info("setClusterPath--> " + this.cluster);

    }

    @Value(value = "${k8s-config.namespace}")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Value(value = "${k8s-config.userSystemPath}")
    public void setUserSystemPath(String userSystemPath) throws IOException {
        if (userSystemPath.startsWith("classpath:")) {
            this.userSystemPath = resourceLoader.getResource(userSystemPath).getFile().getAbsolutePath();
        } else {
            this.userSystemPath = System.getProperty("user.dir") + userSystemPath;
        }
        log.info("setUserSystemPathPath--> " + this.userSystemPath);

    }

    @Value(value = "${cloud-shell.image}")
    public void setCloudShellImage(String cloudShellImage) {
        this.cloudShellImage = cloudShellImage;
    }

}
