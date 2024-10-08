package com.hyperos.cloudshellconfig.response;

import com.hyperos.cloudshellconfig.exception.BaseException;
import com.hyperos.cloudshellconfig.exception.UserSystemException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description： 全局异常处理
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义业务异常处理
     *
     * @param e e
     * @return ResponseData
     */
    @ExceptionHandler(UserSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<String> userSystemException(Exception e) {
        log.error("ex={}", e.getMessage(), e);
        return ResponseData.fail(ResponseCode.RC500.getCode(), e.getMessage());
    }

    /**
     * 默认全局异常处理。
     *
     * @param e e
     * @return ResponseData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(Exception e) {
        log.error("ex={}", e.getMessage(), e);
        return ResponseData.fail(ResponseCode.RC500.getCode(), e.getMessage());
    }

    /**
     * Assert异常
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(IllegalArgumentException e) {
        return ResponseData.fail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), e.getMessage());
    }


    /**
     * 抓取自定义异常  BaseException
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(BaseException e) {
        return ResponseData.fail(e.getErrorCode(), e.getMessage());
    }


    /**
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseData<String>> handleValidatedException(Exception e) {
        ResponseData<String> resp = null;

        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            resp = ResponseData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            resp = ResponseData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            resp = ResponseData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        }

        log.error("abnormal parameter verification:{}", resp.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

}

