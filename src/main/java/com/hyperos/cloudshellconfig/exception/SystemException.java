package com.hyperos.cloudshellconfig.exception;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class SystemException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERROR_CODE = 540;

    public SystemException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public SystemException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public SystemException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public SystemException(int errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }
}

