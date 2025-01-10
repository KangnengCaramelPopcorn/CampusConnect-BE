package com.caramelpopcorn.campusconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    public static class CreateUser{
        String no;
        String password;
        String verificationCode;
        String name;
        String major;
        String email;
        String university;
    }
    @Getter
    @AllArgsConstructor
    public static class loginDto {
        String no;
        String password;
    }
}
