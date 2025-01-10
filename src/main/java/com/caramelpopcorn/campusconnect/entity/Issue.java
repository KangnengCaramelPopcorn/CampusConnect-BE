package com.caramelpopcorn.campusconnect.entity;


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
@NoArgsConstructor
public class Issue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String writer;
    private String category;
    private String content;
    private String solution;
    @Setter
    private State state;
    private String vote_link;
    private Double priority;
    private LocalDateTime created;
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Issue(String title, String writer, String category, String content, String solution, State state, String vote_link,
                 Double priority, LocalDateTime created) {
        this.title = title;
        this.writer = writer;
        this.category = category;
        this.content = content;
        this.solution = solution;
        this.state = state;
        this.vote_link = vote_link;
        this.priority = priority;
        this.created = created;
    }

}