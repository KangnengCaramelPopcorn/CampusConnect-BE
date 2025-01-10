package com.caramelpopcorn.campusconnect.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum IssueErrorCode implements ErrorCode {
    ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이슈를 찾을 수 없습니다."),
    ISSUE_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이벤트 생성 중 오류가 발생했습니다."),
    ISSUE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이슈 업데이트 중 오류가 발생했습니다."),
    UNAUTHORIZED_ISSUE_ACCESS(HttpStatus.FORBIDDEN, "해당 이벤트에 접근할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    IssueErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

