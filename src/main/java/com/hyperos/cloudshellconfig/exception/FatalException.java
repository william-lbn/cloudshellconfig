package com.hyperos.cloudshellconfig.exception;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description： 严重错误异常，需要记录日志告警
 * @modifiedBy：
 * @version: 1.0
 */
public class FatalException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERROR_CODE = 580;

    public FatalException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public FatalException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public FatalException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public FatalException(int errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }
}
