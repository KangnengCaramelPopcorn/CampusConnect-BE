package com.caramelpopcorn.campusconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiReqDto {
    private Long complaint_id;
    private String category;
    private String title;
    private String content;
    private String solution;
    private long priority_score;
    private long comment_count;
    private long like_count;
}
