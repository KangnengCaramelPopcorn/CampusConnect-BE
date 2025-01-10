package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.service.MailService;
import com.caramelpopcorn.campusconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO.CreateUser dto) {
        try {// 사용자 생성
            userService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
