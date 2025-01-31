package com.example.scheduleproject.dto;

import com.example.scheduleproject.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {

    private int statusCode;
    private String message;
    private T data;
    private String errorDetails;

    public ApiResponseDto(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseDto<T> OK(String message, T data) {
        return new ApiResponseDto<>(200, message, data);
    }

    public static <T> ApiResponseDto<T> OK(String message) {
        return new ApiResponseDto<>(200, message, null, null);
    }

    public static <T> ApiResponseDto<T> fail(ErrorCode errorCode) {
        return new ApiResponseDto<>(errorCode.getCode(), errorCode.name(), null, errorCode.getMessage());
    }
}