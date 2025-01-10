package com.caramelpopcorn.campusconnect.repository;

import com.caramelpopcorn.campusconnect.entity.Comment;
import com.caramelpopcorn.campusconnect.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssue(Issue issue);
    long countByIssue(Issue issue);
}