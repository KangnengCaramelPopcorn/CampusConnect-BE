package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.configuration.jwt.CustomUserDetails;
import com.caramelpopcorn.campusconnect.configuration.jwt.TokenService;
import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.entity.User;
import com.caramelpopcorn.campusconnect.global.Role;
import com.caramelpopcorn.campusconnect.global.code.CommonErrorCode;
import com.caramelpopcorn.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createUser(UserDTO.CreateUser dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        User user = new User(dto, Role.STUDENT, encodedPassword, 0L);
        userRepository.save(user);
    }
    public ResponseEntity<?> login(UserDTO.loginDto dto) {

        User user = userRepository.findByNo(dto.getNo())
                .orElseThrow(() -> new RuntimeException(CommonErrorCode.NOT_FOUND_USER_ERROR.getMessage()));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return tokenService.createToken(user.getId(), user.getRole());
    }
    public User getProfile(UserDetails userDetails) {
        return userRepository.findById(Long.parseLong(userDetails.getUsername())).orElseThrow(() -> new RuntimeException(CommonErrorCode.NOT_FOUND_USER_ERROR.getMessage()));
    }
}
