package com.caramelpopcorn.campusconnect.util;

import lombok.Getter;
import lombok.Builder;


@Getter
@Builder
public class Api_Response<T> {
    private String state;
    private String message;
    private T result;
}