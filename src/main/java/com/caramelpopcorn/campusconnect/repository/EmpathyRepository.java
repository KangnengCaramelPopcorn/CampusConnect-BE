package com.caramelpopcorn.campusconnect.repository;

import com.caramelpopcorn.campusconnect.entity.Empathy;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpathyRepository extends JpaRepository<Empathy, Long> {
    Empathy findByIssueAndUser(Issue issue, User user);
    Long countByIssue(Issue issue);
}