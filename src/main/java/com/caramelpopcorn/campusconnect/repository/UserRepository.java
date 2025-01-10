package com.caramelpopcorn.campusconnect.repository;


import com.caramelpopcorn.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByNo(String no);

    Optional<User> findByNoAndPassword(String no, String password);
}