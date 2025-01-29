package com.example.scheduleproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    FAIL(500, "응답 실패"),
    NO_MATCH_PASSWORD_CONFIRMATION(400, "비밀번호 확인 불일치"),
    MISSING_REQUIRED_FIELD(400, "필수 요청 값을 받지 못함"),
    MISSING_PARAMETER_ID(400, "id 파라미터 값을 받지 못함"),
    UNAUTHORIZED(401, "비밀번호 불일치"),
    NOT_FOUND(404, "존재하지 않는 id값");

    private final int code;
    private final String message;
}
