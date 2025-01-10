package com.caramelpopcorn.campusconnect.entity;

import com.caramelpopcorn.campusconnect.dto.UserDTO;
import com.caramelpopcorn.campusconnect.global.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String no;
    private String name;
    private String email;
    private String password;
    private String university;
    private String major;
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserIssue> userIssues = new ArrayList<>();

    public User(UserDTO.CreateUser userDto, Role role, String password) {
        this.no = userDto.getNo();
        this.password = password;
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.university = userDto.getUniversity();
        this.major = userDto.getMajor();
        this.role = role;
    }
    public User(String no, String name) {
        this.no = no;
        this.name = name;
    }
}