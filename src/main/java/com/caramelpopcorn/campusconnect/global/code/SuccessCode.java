package com.caramelpopcorn.campusconnect.global.code;

import lombok.Getter;

@Getter
public enum SuccessCode {

    /**
     * ******************************* Success CodeList ***************************************
     */
    SELECT_SUCCESS(200, "200", "SELECT SUCCESS"),
    SELECT_SUCCESS_NO_CONTENT(204, "200", "NO CONTENT"),
    DELETE_SUCCESS(200, "200", "DELETE SUCCESS"),
    INSERT_SUCCESS(201, "201", "INSERT SUCCESS"),
    UPDATE_SUCCESS(200, "200", "UPDATE SUCCESS"),
    TOKEN_REFRESH_SUCCESS(200, "200", "TOKEN REFRESH SUCCESS"),
    USER_SIGNUP_SUCCESS(200, "200", "USER SIGNUP SUCCESS")
    ; // End

    /**
     * ******************************* Success Code Constructor ***************************************
     */
    // 성공 코드의 '코드 상태'를 반환한다.
    private final int status;

    // 성공 코드의 '코드 값'을 반환한다.
    private final String code;

    // 성공 코드의 '코드 메시지'를 반환한다.s
    private final String message;

    // 생성자 구성
    SuccessCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
