package com.tkx.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 **/
@ControllerAdvice
@Slf4j
public class ExceptionHandle {


    /**
     * @param ex
     */
    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception ex){
        log.info("handle exception start");
    }

    /**
     * @param ex
     */
    @ExceptionHandler(value = RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex){
        log.info("handle runtimeException start");
        log.info(ex.getMessage());
    }
}
