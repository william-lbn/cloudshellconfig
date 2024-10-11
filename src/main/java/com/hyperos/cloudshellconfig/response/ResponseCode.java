package com.hyperos.cloudshellconfig.response;

import lombok.Getter;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description：业务响应码语义
 * @modifiedBy：
 * @version: 1.0
 */
public enum ResponseCode {


    /**
     * 操作成功
     **/
    RC100(100, "Succeed."),

    // CloudShell
    CloudShell100000(100000,  "Cloud Shell already exists."),
    CloudShell100001(100001,  "get secret is error."),
    CloudShell100002(100002,  "create resource error."),
    CloudShell100003(100003,  "get resource error."),
    CloudShell100004(100004,  "create secret error."),
    CloudShell100005(100005,  "create k8s client error."),



    /**
     * 操作失败
     **/
    RC999(999, "Failed."),


    /**
     * access_denied
     **/
    RC403(403, "No access rights, please contact the administrator to grant permissions."),

    /**
     * access_denied
     **/
    RC401(401, "Exception when anonymous users access resources without permissions."),

    /**
     * 服务异常
     **/
    RC500(500, "The system is abnormal. Please try again later."),

    /**
     * 创建数据库服务异常
     **/

    RC501(501, "The system is abnormal. Please try again later."),

    RC502(502, "The database connect error. Please try again later."),


    ILLEGAL_ARGUMENT(3001, "Illegal parameter."),
    ILLEGAL_ARGUMENT_TIME_FLAG(3002, "timeFlag is error, the value must be 0,1,2."),

    ILLEGAL_ARGUMENT_TIME(3003, "string conversion Date failed."),

    INVALID_TOKEN(2001, "The access token is illegal."),

    ACCESS_DENIED(2003, "You do not have permission to access this resource.");

    /**
     * 自定义状态码
     **/
    @Getter
    private final int code;
    /**
     * 自定义描述
     **/
    @Getter
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
