package com.caramelpopcorn.campusconnect.configuration.jwt;

import com.caramelpopcorn.campusconnect.global.code.CommonErrorCode;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        System.out.println(
                jwtUtil.parseToken(refreshToken).getExpiration());
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.MISSING_REFRESH_TOKEN);
        }

        try {
            System.out.println(
                    jwtUtil.parseToken(refreshToken).getExpiration());
            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ApiResponseUtil.createErrorResponse(CommonErrorCode.EXPIRED_REFRESH_TOKEN);
            }
            String userId = jwtUtil.getUserId(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            System.out.println(tokenService.createToken(userId, role).getBody());
            return tokenService.createToken(userId, role);
        } catch (IllegalStateException e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.INVALID_REFRESH_TOKEN);
        } catch (Exception e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
