package com.caramelpopcorn.campusconnect.configuration.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // 사용자 정보를 조회할 리포지토리

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터베이스에서 사용자 조회
        com.caramelpopcorn.campusconnect.entity.User userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // UserDetails 객체 생성 및 반환
        return User.builder()
                .username(userEntity.getUserId())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().toArray(String[]::new)) // 역할 설정
                .build();
    }
}
