package com.caramelpopcorn.campusconnect.entity;


import com.caramelpopcorn.campusconnect.dto.MergeIssueResDto;
import com.caramelpopcorn.campusconnect.global.State;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Issue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    private String category;
    private String content;
    private String solution;
    @Setter
    private State state;
    private String vote_link;
    private Double priority;
    private LocalDateTime created;
    private Integer comment_counts;
    private Integer like_counts;
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserIssue> userIssues = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Issue(String title, String category, String content, String solution, State state, String vote_link,
                 Double priority, LocalDateTime created, User user) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.solution = solution;
        this.state = state;
        this.vote_link = vote_link;
        this.priority = priority;
        this.created = created;
    }
    public void sync(MergeIssueResDto mergeIssueResDto) {
        this.priority = Double.valueOf(mergeIssueResDto.getPriority_score());
        this.title = mergeIssueResDto.getMerged_title();
        this.content = mergeIssueResDto.getAggregated_content();
    }
    public Issue(String name, String title, String category, String content, String solution, State state, Integer like_counts, Integer comment_counts, Double priority, LocalDateTime created) {
        this.name = name;
        this.title = title;
        this.category = category;
        this.content = content;
        this.solution = solution;
        this.state = state;
        this.like_counts = like_counts;
        this.comment_counts = comment_counts;
        this.priority = priority;
        this.created = created;
    }
}