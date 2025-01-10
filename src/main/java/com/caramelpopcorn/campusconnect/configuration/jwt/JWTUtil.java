package com.caramelpopcorn.campusconnect.configuration.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {
    public final static String ACCESS = "ACCESS_TOKEN";
    public final static String REFRESH = "REFRESH_TOKEN";

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    @Value("${spring.jwt.access_expired_ms}")
    private Long accessExpiredMs;

    @Value("${spring.jwt.refresh_expired_ms}")
    private Long refreshExpiredMs;

    public JWTUtil(@Value("${spring.jwt.secret_access}") String accessKey, @Value("${spring.jwt.secret_refresh}") String refreshKey) {
        this.accessSecretKey = new SecretKeySpec(accessKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshSecretKey = new SecretKeySpec(refreshKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    public String createToken(String type, String userId, String role) {
        if (type.equals(ACCESS)) {
            return createAccessToken(userId, role);
        } else {
            return createRefreshToken(userId, role);
        }
    }
    private String createAccessToken(String userId, String role) {
        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(currentTime.getTime() + accessExpiredMs);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .claim("type", ACCESS)
                .issuedAt(currentTime)
                .expiration(expirationTime)
                .signWith(accessSecretKey)
                .compact();
    }
    private String createRefreshToken(String userId, String role) {
        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(currentTime.getTime() + refreshExpiredMs);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .claim("type", REFRESH)
                .issuedAt(currentTime)
                .expiration(expirationTime)
                .signWith(accessSecretKey)
                .compact();
    }
    public Claims parseToken(String token) {
        try {
            // Access Token 검증
            return Jwts.parser()
                    .setSigningKey(accessSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Refresh Token 검증
            return Jwts.parser()
                    .setSigningKey(refreshSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
    public String getUserId(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }
    public String getRole(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", String.class); // JWT의 "role" 클레임 반환
    }
    public int getRefreshTokenExpirySeconds() {
        return (int) (refreshExpiredMs / 1000); // Milliseconds -> Seconds 변환
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = parseToken(refreshToken);
        if (!claims.get("type", String.class).equals(REFRESH)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String userId = getUserId(refreshToken);
        String role = getRole(refreshToken);
        return createAccessToken(userId, role);
    }

    public Cookie createHttpOnlySecureCookie(String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge((int) (refreshExpiredMs / 1000));
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

//    public Cookie createCookie(String refreshToken) {
//        Cookie cookie = new Cookie("refresh", refreshToken);
//        cookie.setMaxAge((int) (refreshExpiredMs / 1000));
//        cookie.setPath("/");
//        return cookie;
//    }
}
