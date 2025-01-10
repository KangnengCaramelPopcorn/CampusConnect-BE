package com.caramelpopcorn.campusconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentReqDto {
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime date;
}
