package com.hyperos.cloudshellconfig.service;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2024/10/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface CloudShellService {

    /**
     * 创建cloud shell
     * @param serviceName serviceName
     * @return cloud shell 地址
     */
    String createCloudShell(String serviceName);

}
