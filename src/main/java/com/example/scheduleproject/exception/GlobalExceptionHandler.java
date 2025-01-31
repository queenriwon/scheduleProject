package com.example.scheduleproject.exception;

import com.example.scheduleproject.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoMatchPasswordConfirmation.class)
    protected ApiResponseDto<String> noMatchPasswordConfirmationHandler(NoMatchPasswordConfirmation ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.NO_MATCH_PASSWORD_CONFIRMATION);
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ApiResponseDto<String> invalidRequestExceptionHandler(InvalidRequestException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.MISSING_REQUIRED_FIELD);
    }

    @ExceptionHandler(IdNotFoundException.class)
    protected ApiResponseDto<String> idNotFoundExceptionHandler(IdNotFoundException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    protected ApiResponseDto<String> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingRequestParameterException.class)
    protected ApiResponseDto<String> missingRequestParameterExceptionHandler(MissingRequestParameterException ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.MISSING_PARAMETER_ID);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseDto<String> handleValidationExceptionHandler(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("입력값이 올바르지 않습니다.");

        return new ApiResponseDto<>(400, "BAD_REQUEST", null, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    protected ApiResponseDto<String> handleGeneralException(Exception ex) {
        log.error("예외 발생 = {}", ex.getMessage());
        return ApiResponseDto.fail(ErrorCode.FAIL);
    }
}