package com.hyperos.cloudshellconfig.controller;

import com.hyperos.cloudshellconfig.response.ResponseData;
import com.hyperos.cloudshellconfig.service.CloudShellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2024/10/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/hyperos/api/cloudshell")
public class KubeConfigController {

    @Autowired
    private CloudShellService cloudShellService;

    @PatchMapping("/{teamName}")
    public ResponseData<?> cloudShellController(@PathVariable("teamName") String teamName) {

        String url = cloudShellService.createCloudShell(teamName);

        return ResponseData.success(url);
    }

}
