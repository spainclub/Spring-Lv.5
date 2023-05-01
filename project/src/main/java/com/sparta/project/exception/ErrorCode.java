package com.sparta.project.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 400 Bad Request
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, "400", "토큰이 유효하지 않습니다."),
    USERNAME_ERROR(HttpStatus.BAD_REQUEST, "400", "작성자만 삭제/수정할 수 있습니다."),
    LOGIN_USERNAME(HttpStatus.BAD_REQUEST, "400", "중복된 username 입니다."),
    LOGIN_NOTFOUND(HttpStatus.BAD_REQUEST, "400", "회원을 찾을 수 없습니다."),
    SIGNUP(HttpStatus.BAD_REQUEST, "400", "양식에 맞는 아이디 / 비밀번호를 입력해주세요.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
