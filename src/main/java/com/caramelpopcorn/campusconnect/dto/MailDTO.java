package com.caramelpopcorn.campusconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MailDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class send{
        private String mail;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class confirmNumber{
        private String enteredNumber;
    }
}