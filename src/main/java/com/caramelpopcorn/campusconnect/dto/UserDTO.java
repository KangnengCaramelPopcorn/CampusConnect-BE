package com.caramelpopcorn.campusconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    public static class CreateUser{
        String studentId;
        String password;
        String verificationCode;
        String name;
        String department;
        String email;
    }
}
