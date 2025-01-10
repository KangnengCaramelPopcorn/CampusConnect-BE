package com.caramelpopcorn.campusconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MergeIssueResDto {
    List<Long> complaint_id;
    Integer priority_score;
    String merged_title;
    String aggregated_content;
}
