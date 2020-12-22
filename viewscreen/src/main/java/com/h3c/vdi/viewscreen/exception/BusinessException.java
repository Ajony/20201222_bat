package com.h3c.vdi.viewscreen.exception;

/**
 * @author lgq
 * @since 2020-5-25
 */
public class BusinessException extends BaseException {
    private static final long serialVersionUID = -6084356855262150534L;

    public BusinessException(String code, String message) {
        super(code, message);
    }
}