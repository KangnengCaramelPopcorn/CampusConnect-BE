package com.caramelpopcorn.campusconnect.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode {
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레쉬 토큰이 누락 된 요청"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"만료된 리프레쉬 토큰"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레쉬 토큰"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    FAIL_SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "회원 가입 실패"),
    NOT_FOUND_USER_ERROR(HttpStatus.NOT_FOUND, "사용자 조회 실패"),
    FAIL_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "올바르지 않은 사용자 정보")
    ;
    private final HttpStatus httpStatus;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
