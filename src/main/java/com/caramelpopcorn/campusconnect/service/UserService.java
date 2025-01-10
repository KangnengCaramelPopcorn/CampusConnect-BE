package com.caramelpopcorn.campusconnect.service;


import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.entity.User;
import com.caramelpopcorn.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(UserDTO.CreateUser dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .no(dto.getStudentId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encodedPassword)
                .university("강남대학교")
                .major(dto.getDepartment())
                .role("STUDENT")
                .build();
        return userRepository.save(user);
    }
}
