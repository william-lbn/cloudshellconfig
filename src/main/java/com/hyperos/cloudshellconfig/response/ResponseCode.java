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

    /**
     * 数据库密码入库服务异常
     **/
    RC101(101, "The database password is inserted into the database service exception."),

    /**
     * 数据库密码更新插入数据库服务异常
     **/
    RC102(102, "Database password update insert database service exception."),

    /**
     * 数据库根据teamId查询数据库服务异常
     **/
    RC103(103, "Database query database service exception based on teamId."),

    /**
     * 数据库根据teamId、developer 查询数据库服务异常
     **/
    RC104(104, "Database query database service exception by teamId and developer."),

    /**
     * 数据库应用授权异常
     **/
    RC105(105, "Database application authorization exception."),

    /**
     * 数据库查询应用授权异常
     **/
    RC106(106, "Database query application authorization exception."),

    /**
     * 数据库查询应用授权异常
     **/
    RC107(107, "delete user dateBase error."),

    RC108(108, "mysql user duplicate!!!."),

    RC109(109, "sql error. sql user or password  contains illegal characters."),

    RC110(110, "sql error. create db user error."),
    RC111(111, "sql error. update db user error."),



    /**
     * 蓝图估算价格失败 错误码 50开头
     **/
    RCBlueprint(200, "Blueprint estimate price success."),
    RCBlueprint50001(50001, "Blueprint failed to estimate price."),
    RCBlueprint50002(50002, "the config of deploymentTask id does not exists."),
    RCBlueprint50003(50003, "the related develop team  does not exists."),
    RCBlueprint50004(50004, "network interSCNID param cannot be null."),
    RCBlueprint50005(50005, "forceSatisfy cannot be null."),
    RCBlueprint50006(50006, "calculate network error."),
    RCBlueprint50007(50007, "calculate description error."),
    RCBlueprint50008(50008, "description param cannot be null."),
    RCBlueprint50009(50009, "networkConfigList cannot be null."),
    RCBlueprint50010(50010, "computeConfigList cannot be null."),
    RCBlueprint50011(50011, "check Database Service success."),
    RCBlueprint50012(50012, "dataService must be not null."),
    RCBlueprint50013(50013, "dataService only supports mysql."),
    RCBlueprint50014(50014, "get master IP from rm by filedName error."),
    RCBlueprint50015(50015, "resource bind error."),
    RCBlueprint50016(50016, "osDeploymentTask is null."),
    RCBlueprint50017(50017, "osDeploymentTaskByTaskBusinessId is null."),
    RCBlueprint50018(50018, "no release plan found."),

    RCBlueprint50019(50019, "get filedName from gaia is null."),
    RCBlueprint50020(50020, "get filedName from gaia is empty."),
    RCBlueprint50021(50021, "gaia component field name uri is error."),

    RCBlueprint50022(50022, "Blueprint deploy error."),
    RCBlueprint50023(50023, "Blueprint deploy success."),

    RCBlueprint50024(50024, "Delete blue task success."),

    RCBlueprint50025(50025, "Delete blue task error."),

    RCBlueprint50026(50026, "Delete blue task error."),

    RCBlueprint50027(50027, "Delete blue task error by gaia description."),
    RCBlueprint50028(50028, "Delete blue task error by gaia network."),

    RCBlueprint50029(50029, "Delete blue task error by database."),

    RCBlueprint50030(50030, "dataService is null and not to check."),

    RCBlueprint50031(50031, "Failed to parse user information."),

    RCBlueprint50032(50032, "request token is null."),
    RCBlueprint50033(50033, "sign in central system is failed."),

    RCBlueprint50034(50034, "sign in cannot get cookie."),

    RCBlueprint50035(50035, "sign in cannot get cookie `HyperOS`\""),
    RCBlueprint50036(50036, "send order error."),

    RCBlueprint50037(50037, "delete  app clone error."),

    RCBlueprint50038(50038, "delete  app clone internal error."),
    RCBlueprint50039(50039, "delete  app clone success."),

    RCBlueprint50040(50040, "calculate frontEnd description error."),

    RCBlueprint50041(50041, "get description status error."),

    RCBlueprint50042(50042, "description not exist."),

    RCBlueprint50043(50043, "no blueprint configuration found."),

    RCBlueprint50044(50044, "blueprint not configured."),

    RCBlueprint50045(50045, "there is no application in the blueprint."),


    //监控和日志
    RCMonitorANDLOG60001(60001, "pageNumber and pageSize must be greater than or equal to 0."),
    RCMonitorANDLOG60002(60002, "Blueprint version is not found by sys_id and env_id."),
    RCMonitorANDLOG60003(60003, "Deployment task is not found by blueVersionId."),
    RCMonitorANDLOG60004(60004, "component not found."),
    RCMonitorANDLOG60005(60005, " get log from metis Request Timeout."),

    RCMonitorANDLOG60006(60006, " get log from metis error."),

    //自动化测试
    RCTestPressure70010(70010, "pressure task description error"),
    RCTestAbility70020(70020, "service ability task description error"),

    RCDomain80000(80000, "update domain db error."),

    RCDomain80001(80001, "insert domain db error."),

    RCDomain80002(80002, "query domain db error."),

    RCDomain80003(80003, "Please keep the domain and sid unique."),

    RCDomain80004(80004, "domain cr apply error."),

    RCDomain80005(80005, "delete domain db error."),

    RCDomain80006(80006, "app Type is backend, sid is null"),
    RCDomain80007(80007,  "sid is null"),
    RCDomain80008(80008,  "sid is invalid, not belong to appId"),
    RCDomain80009(80009,  "appId is null"),
    RCDomain80010(80010,  "domain is duplicate"),
    RCDomain80011(80011,  "domain is null"),


    // CloudShell
    CloudShell90000(90000,  "get secret is error."),
    CloudShell90001(90001,  "create resource error."),
    CloudShell90002(90002,  "get resource error."),
    CloudShell90003(90003,  "create secret error."),
    CloudShell90004(90004,  "create k8s client error."),


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
