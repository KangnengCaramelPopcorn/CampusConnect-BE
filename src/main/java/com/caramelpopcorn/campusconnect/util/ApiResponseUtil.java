package com.caramelpopcorn.campusconnect.util;



import com.caramelpopcorn.campusconnect.global.code.ErrorCode;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    public static <T> ResponseEntity<Api_Response<T>> createResponse(int status, String message, T result) {
        Api_Response<T> response = Api_Response.<T>builder()
                .state(String.valueOf(status))
                .message(message)
                .result(result)
                .build();
        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<Api_Response<T>> createSuccessResponse(SuccessCode successCode, T result) {
        return createResponse(successCode.getStatus(), successCode.getMessage(), result);
    }

    public static <T> ResponseEntity<Api_Response<T>> createSuccessResponse(String message, T result) {
        return createResponse(HttpStatus.OK.value(), message, result);  // 200 OK 반환
    }

    public static <T> ResponseEntity<Api_Response<T>> createErrorResponse(ErrorCode errorCode) {
        return createResponse(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
    }

    public static <T> ResponseEntity<Api_Response<T>> createErrorResponse(ErrorCode errorCode, T result) {
        return createResponse(errorCode.getHttpStatus().value(), errorCode.getMessage(), result);
    }
}
