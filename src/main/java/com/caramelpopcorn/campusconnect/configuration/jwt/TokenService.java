package com.caramelpopcorn.campusconnect.configuration.jwt;

import com.caramelpopcorn.campusconnect.global.Role;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;
    public ResponseEntity<?> createToken(String userId, String role) {
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
    }
    public ResponseEntity<?> createToken(Long userId, Role role) {
        return createToken(String.valueOf(userId), String.valueOf(role));
    }
}
