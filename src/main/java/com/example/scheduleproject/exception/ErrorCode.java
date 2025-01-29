package com.example.scheduleproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "잘못된 응답"),
    UNAUTHORIZED(401, "비밀번호 불일치"),
    NOT_FOUND(404, "존재하지 않는 id값");

    private int code;
    private String message;
}
