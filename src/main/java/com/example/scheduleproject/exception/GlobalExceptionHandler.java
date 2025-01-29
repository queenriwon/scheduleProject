package com.example.scheduleproject.exception;

import com.example.scheduleproject.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoMatchPasswordConfirmation.class)
    protected ApiResponse<String> noMatchPasswordConfirmationHandler(NoMatchPasswordConfirmation ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.NO_MATCH_PASSWORD_CONFIRMATION);
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ApiResponse<String> invalidRequestExceptionHandler(InvalidRequestException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.MISSING_REQUIRED_FIELD);
    }

    @ExceptionHandler(IdNotFoundException.class)
    protected ApiResponse<String> idNotFoundExceptionHandler(IdNotFoundException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    protected ApiResponse<String> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingRequestParameterException.class)
    protected ApiResponse<String> missingRequestParameterExceptionHandler(MissingRequestParameterException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.MISSING_PARAMETER_ID);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationExceptionHandler(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("입력값이 올바르지 않습니다.");

        return new ApiResponse<>(400, "BAD_REQUEST", errorMessage);
    }

    @ExceptionHandler(Exception.class)
    protected ApiResponse<String> handleGeneralException(Exception ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponse.fail(ErrorCode.FAIL);
    }
}