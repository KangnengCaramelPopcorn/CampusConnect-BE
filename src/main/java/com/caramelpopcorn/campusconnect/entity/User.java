package com.caramelpopcorn.campusconnect.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "university")
    private String university;

    @Column(name = "major")
    private String major;

    @Column(name = "role")
    private String role;

    @Builder
    public User(String no, String name, String email, String password, String university,
                String major, String role) {
        this.no = no;
        this.name = name;
        this.email = email;
        this.password = password;
        this.university = university;
        this.major = major;
        this.role = role;
    }
}