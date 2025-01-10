package com.caramelpopcorn.campusconnect.repository;

import com.caramelpopcorn.campusconnect.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IssueRepository extends JpaRepository<Issue, Long> {

}