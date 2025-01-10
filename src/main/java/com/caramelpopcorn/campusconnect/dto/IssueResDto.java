package com.caramelpopcorn.campusconnect.dto;

import com.caramelpopcorn.campusconnect.global.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class IssueResDto {
    private String title;
    private String writer;
    private String category;
    private String content;
    private String solution;
    private State state;
    private String vote_link;
    private LocalDateTime created;
    private Long empathyCount;
    private Long voteCount;
    private Long commentCount;
}
