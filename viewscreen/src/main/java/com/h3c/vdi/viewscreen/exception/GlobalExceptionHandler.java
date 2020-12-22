package com.h3c.vdi.viewscreen.exception;

import com.google.gson.JsonSyntaxException;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.util.Objects;

/**
 * 全局异常处理
 */
@RestControllerAdvice()
//@ResponseStatus
@Log4j2
public class GlobalExceptionHandler {


    /**
     * 未定义异常
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResponse<?> errorHandle(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.S00001, ex.getMessage());
    }

    /**
     * 业务异常
     */

    @ExceptionHandler(value = BusinessException.class)
    public ApiResponse<?> errorHandle(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ex.code, ex.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<?> errorHandle(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.E00100, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(value = ValidationException.class)
    public ApiResponse<?> errorHandle(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.E00100, ex.getCause().getMessage());
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ApiResponse<?> errorHandle(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.E20000, ex.getMessage());
    }


    /**
     * JSON解析异常
     */
    @ExceptionHandler(value = JsonSyntaxException.class)
    public ApiResponse<?> errorHandle(JsonSyntaxException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.E30000, ex.getMessage());
    }

    /**
     * 无权限异常
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ApiResponse<?> errorHandle(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.buildError(ApiErrorEnum.S40003, ex.getMessage());
    }


}