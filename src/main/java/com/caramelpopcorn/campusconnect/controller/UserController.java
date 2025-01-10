package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.configuration.jwt.CustomUserDetails;
import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.entity.User;
import com.caramelpopcorn.campusconnect.global.code.CommonErrorCode;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.service.MailService;
import com.caramelpopcorn.campusconnect.service.UserService;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO.CreateUser dto) {
        try {// 사용자 생성
            userService.createUser(dto);
            return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.FAIL_SIGNUP_ERROR);
        }
    }
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.loginDto dto) {
        try {
            return userService.login(dto);
        } catch (RuntimeException e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.FAIL_LOGIN_ERROR);
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getProfile(userDetails);
        if (user == null) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.NOT_FOUND_USER_ERROR);
        } else {
            return ApiResponseUtil.createSuccessResponse(SuccessCode.SELECT_SUCCESS, user);
        }
    }
}
