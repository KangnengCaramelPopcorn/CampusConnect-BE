package com.caramelpopcorn.campusconnect.dto;

import com.caramelpopcorn.campusconnect.global.State;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class IssueReqDto {
    private String title;
    private String content;
    private String solution;
    private State state;
    private String vote_link;
    private LocalDateTime created;
}