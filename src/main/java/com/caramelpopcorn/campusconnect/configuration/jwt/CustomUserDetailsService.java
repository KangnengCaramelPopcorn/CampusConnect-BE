package com.caramelpopcorn.campusconnect.configuration.jwt;

import com.caramelpopcorn.campusconnect.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 데이터베이스에서 사용자 조회
        com.caramelpopcorn.campusconnect.entity.User userEntity = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id: " + userId));

        return User.builder()
                .username(String.valueOf(userEntity.getId()))
                .password(userEntity.getPassword())
                .authorities(String.valueOf(userEntity.getRole()))
                .build();
    }
}
