package com.caramelpopcorn.campusconnect.util;

import lombok.Getter;

@Getter
public class API_Wrapper<T> {
    private String status;
    private String message;
    private T data;

    public API_Wrapper(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}