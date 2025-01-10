package com.caramelpopcorn.campusconnect.entity;

import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.global.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "no", nullable = false, unique = true)
    private String no;
    private String name;
    private String email;
    private String password;
    private String university;
    private String major;
    private Role role;
    private Long score;

    public User(UserDTO.CreateUser userDto, Role role, String password, Long score) {
        this.no = userDto.getNo();
        this.password = password;
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.university = userDto.getUniversity();
        this.major = userDto.getMajor();
        this.role = role;
        this.score = score;
    }
}