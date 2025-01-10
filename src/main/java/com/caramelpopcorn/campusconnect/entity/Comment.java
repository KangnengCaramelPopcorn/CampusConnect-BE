package com.caramelpopcorn.campusconnect.entity;

import com.caramelpopcorn.campusconnect.global.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private LocalDate date;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    @JsonIgnore
    private Issue issue;

    @Builder
    public Comment(String writer, LocalDate date, String content, Issue issue) {
        this.writer = writer;
        this.date = date;
        this.content = content;
        this.issue = issue;
    }
    public void mergeComment(Issue issue) {
        this.issue = issue;
    }

}
