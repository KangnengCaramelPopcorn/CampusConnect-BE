package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.dto.CommentReqDto;
import com.caramelpopcorn.campusconnect.entity.Comment;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.repository.CommentRepository;
import com.caramelpopcorn.campusconnect.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    public Comment createComment(CommentReqDto commentReqDto) {
        Issue issue = issueRepository.findById(commentReqDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이슈가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .writer(commentReqDto.getWriter())
                .date(commentReqDto.getDate().toLocalDate())
                .content(commentReqDto.getContent())
                .issue(issue)
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByIssueId(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이슈가 존재하지 않습니다."));

        return commentRepository.findByIssue(issue);
    }

    public long getCommentCountByIssueId(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이슈가 존재하지 않습니다."));

        return commentRepository.countByIssue(issue); // 댓글 개수 반환
    }
}
