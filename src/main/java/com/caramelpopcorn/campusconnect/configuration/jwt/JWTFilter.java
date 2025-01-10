package com.caramelpopcorn.campusconnect.configuration.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final List<String> freePathList;
    private final UserDetailsService userDetailsService;


    public JWTFilter(JWTUtil jwtUtil, List<String> freePathList, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.freePathList = freePathList;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (freePathList.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = extractToken(request);
            if (jwt == null || jwt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtUtil.isTokenExpired(jwt)) {
                throw new IllegalStateException("JWT token is expired");
            }
            Claims claims = jwtUtil.parseToken(jwt);
            String userId = claims.getSubject();
            System.out.println("!");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            log.error("JWT authentication failed: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 문자열 반환
        }
        return null;
    }
}
