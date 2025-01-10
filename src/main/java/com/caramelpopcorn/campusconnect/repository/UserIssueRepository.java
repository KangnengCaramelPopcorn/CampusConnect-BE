package com.caramelpopcorn.campusconnect.repository;

import com.caramelpopcorn.campusconnect.entity.UserIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserIssueRepository extends JpaRepository<UserIssue, Long> {
    List<UserIssue> findByUserId(Long userId);
    List<UserIssue> findByIssueId(Long issueId);
}
