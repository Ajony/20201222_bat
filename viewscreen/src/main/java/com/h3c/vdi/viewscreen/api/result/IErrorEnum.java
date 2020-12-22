package com.h3c.vdi.viewscreen.api.result;

import java.io.Serializable;
/**
 * @author lgq
 * @since 2020-5-25
 */
public interface IErrorEnum<T extends Serializable> {

    String getName();

    T getValue();

    String getMessage();

    public static String getMessageByValue(Integer value) {
        return "";
    }

    IErrorEnum getErrorEnumByName(String name);
}