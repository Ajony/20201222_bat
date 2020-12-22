package com.h3c.vdi.viewscreen.dto.rs;

/**
 * Created by x19765 on 2020/10/19.
 */

import java.io.Serializable;

/**
 * 封装与Web REST交互的结果
 */
public class RestResult implements Serializable {

    private static final long serialVersionUID = 4304320001183871677L;

    /** 成功 */
    public static final int SUCCESS = 0;

    /** 失败 */
    public static final int FAIL = 1;

    /** 部分成功 */
    public static final int PART_SUCCESS = 2;

    /** 处理结果，成功、失败、部分成功 */
    private int result;

    /** 错误码，失败的时候才有值 */
    private Integer errorCode;

    /** 提示信息，根据处理结果决定是成功信息还是失败信息 */
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "result=" + result +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
