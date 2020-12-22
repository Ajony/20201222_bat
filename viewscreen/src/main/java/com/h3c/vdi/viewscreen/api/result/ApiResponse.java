package com.h3c.vdi.viewscreen.api.result;
/**
 * @author lgq
 * @since 2020-5-25
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@SuppressWarnings("all")
public class ApiResponse<T> {
    private ApiResponseType result;
    private String code;
    private String message;
    private List<String> detailMessages;
    private T data;

    public ApiResponse(ApiResponseType result) {
        this.result = result;
    }

    public static <T> ApiResponse<T> buildSuccess(T data) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.SUCCESS);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildSuccess(T data, String code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.SUCCESS);
        apiResponse.setData(data);
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildSuccess(T data, String code, List<String> detailMessages) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.SUCCESS);
        apiResponse.setData(data);
        apiResponse.setCode(code);
        apiResponse.setDetailMessages(detailMessages);
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildError(IErrorEnum apiErrorEnum, String message) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.ERROR);
        apiResponse.setCode(apiErrorEnum.getName());
        apiResponse.setMessage(String.format(apiErrorEnum.getMessage(), message));
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildError(T data, IErrorEnum apiErrorEnum, String message) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.ERROR);
        apiResponse.setData(data);
        apiResponse.setCode(apiErrorEnum.getName());
        apiResponse.setMessage(String.format(apiErrorEnum.getMessage(), message));
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildError(String code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.ERROR);
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static <T> ApiResponse<T> buildError(String code, List<String> detailMessages) {
        ApiResponse<T> apiResponse = new ApiResponse(ApiResponseType.ERROR);
        apiResponse.setCode(code);
        apiResponse.setDetailMessages(detailMessages);
        return apiResponse;
    }

}