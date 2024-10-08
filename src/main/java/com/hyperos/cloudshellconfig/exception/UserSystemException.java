package com.hyperos.cloudshellconfig.exception;

import com.hyperos.cloudshellconfig.response.ResponseCode;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description： 自定义业务异常，需要有明确的业务语义
 * @modifiedBy：
 * @version: 1.0
 */
public class UserSystemException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERROR_CODE = 510;

    public UserSystemException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public UserSystemException(ResponseCode responseCode) {
        super(responseCode.getCode(), responseCode.getMessage());
    }

    public UserSystemException(ResponseCode responseCode, String errorMessage) {
        super(responseCode.getCode(), errorMessage);
    }


    public UserSystemException(String message, Object... data) {
        super(DEFAULT_ERROR_CODE, MessageFormatter.arrayFormat(message, data).getMessage());
    }

    public UserSystemException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public UserSystemException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public UserSystemException(int errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }
}
