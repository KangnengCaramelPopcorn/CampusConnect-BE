package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueResDto;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.global.State;
import com.caramelpopcorn.campusconnect.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final CommentService commentService;
    private final EmpathyService empathyService;

    public Issue createIssue(IssueReqDto issueReqDto, String writer) {
        Issue issue = Issue.builder()
                .title(issueReqDto.getTitle())
                .writer(writer)
                .category(issueReqDto.getCategory())
                .content(issueReqDto.getContent())
                .solution(issueReqDto.getSolution())
                .state(issueReqDto.getState())
                .vote_link(issueReqDto.getVote_link())
                .priority(0.0) // priority는 일단 0.0
                .created(issueReqDto.getCreated())
                .build();
        return issueRepository.save(issue);
    }

    public List<IssueResDto> getAllIssues() {
        List<Issue> issues = issueRepository.findAll();

        return issues.stream().map(issue -> {
            IssueResDto issueResDto = new IssueResDto();
            issueResDto.setTitle(issue.getTitle());
            issueResDto.setWriter(issue.getWriter());
            issueResDto.setCategory(issue.getCategory());
            issueResDto.setContent(issue.getContent());
            issueResDto.setSolution(issue.getSolution());
            issueResDto.setState(issue.getState());
            issueResDto.setVote_link(issue.getVote_link());
            issueResDto.setCreated(issue.getCreated());
            issueResDto.setEmpathyCount(empathyService.getEmpathyCountByIssueId(issue.getId()));
            issueResDto.setCommentCount(commentService.getCommentCountByIssueId(issue.getId())); // 댓글 수
            issueResDto.setVoteCount(null);
            return issueResDto;
        }).collect(Collectors.toList());
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 이슈를 찾을 수 없습니다: " + id));
    }

    public Issue updateIssueState(Long id, State state) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 이슈를 찾을 수 없습니다: " + id));

        issue.setState(state);

        return issueRepository.save(issue);
    }
}
