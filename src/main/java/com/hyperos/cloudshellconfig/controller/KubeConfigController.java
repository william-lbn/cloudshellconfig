package com.hyperos.cloudshellconfig.controller;

import com.hyperos.cloudshellconfig.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
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

    @PatchMapping("/{serviceName}")
    public ResponseData<?> cloudShellController(@PathVariable("serviceName") String serviceName) {

        return ResponseData.success();
    }

}
