package com.caramelpopcorn.campusconnect.configuration;

import com.caramelpopcorn.campusconnect.configuration.jwt.CustomUserDetailsService;
import com.caramelpopcorn.campusconnect.configuration.jwt.JWTFilter;
import com.caramelpopcorn.campusconnect.configuration.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final List<String> freePathList = new ArrayList<>(List.of("/", "/env", "/hc", "/swagger-ui/index.html", "/api/user/signup", "/api/user/login", "/api/issue/ai/mergeissue", "/api/issue/test1","/api/issue/test2"));
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(freePathList.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTFilter(jwtUtil, freePathList, userDetailsService), UsernamePasswordAuthenticationFilter.class);


//        http.cors(cors -> cors.configurationSource(request -> {
//            var config = new org.springframework.web.cors.CorsConfiguration();
//            config.setAllowedOrigins(List.of("http://localhost:3000"));
//            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            config.setAllowedHeaders(List.of("*"));
//            config.setAllowCredentials(true);
//            return config;
//        }));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
