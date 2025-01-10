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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.MISSING_REFRESH_TOKEN);
        }

        try {
            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ApiResponseUtil.createErrorResponse(CommonErrorCode.EXPIRED_REFRESH_TOKEN);
            }

            String userId = jwtUtil.getUserId(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            String newAccessToken = jwtUtil.createToken(JWTUtil.ACCESS, userId, role);

            String newRefreshToken = jwtUtil.createToken(JWTUtil.REFRESH, userId, role);
            Cookie refreshCookie = jwtUtil.createHttpOnlySecureCookie(newRefreshToken);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, ResponseCookie.from(refreshCookie.getName(), refreshCookie.getValue())
                            .httpOnly(true)
                            .secure(true)
                            .path(refreshCookie.getPath())
                            .maxAge(refreshCookie.getMaxAge())
                            .build().toString()
                    )
                    .body(ApiResponseUtil.createSuccessResponse(
                            SuccessCode.TOKEN_REFRESH_SUCCESS,
                            Map.of("access_token", newAccessToken)
                    ));

        } catch (IllegalStateException e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.INVALID_REFRESH_TOKEN);
        } catch (Exception e) {
            return ApiResponseUtil.createErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
