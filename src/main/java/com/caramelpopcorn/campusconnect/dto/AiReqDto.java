package com.caramelpopcorn.campusconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiReqDto {
    private Long id;
    private String title;
    private String content;
    private Long likeCount;
    private Long voteCount;
    private String category;
}
