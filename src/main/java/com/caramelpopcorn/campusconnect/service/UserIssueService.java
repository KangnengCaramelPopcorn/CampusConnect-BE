package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.dto.MergeIssueResDto;
import com.caramelpopcorn.campusconnect.entity.Comment;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.entity.User;
import com.caramelpopcorn.campusconnect.entity.UserIssue;
import com.caramelpopcorn.campusconnect.repository.IssueRepository;
import com.caramelpopcorn.campusconnect.repository.UserIssueRepository;
import com.caramelpopcorn.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserIssueService {
    private final UserIssueRepository userIssueRepository;
    private final IssueRepository issueRepository;

    public void mergeIssues(MergeIssueResDto mergeIssueResDto) {
        List<Long> issueIds = mergeIssueResDto.getComplaint_id();

        // 가장 작은 이슈 ID 찾기
        Long smallestIssueId = issueIds.stream().min(Long::compareTo).orElseThrow();

        // 기준 이슈 가져오기
        Issue smallestIssue = issueRepository.findById(smallestIssueId)
                .orElseThrow(() -> new IllegalArgumentException("Smallest issue not found"));

        // 나머지 이슈들 가져오기
        List<Issue> otherIssues = issueRepository.findAllById(issueIds);
        otherIssues.removeIf(issue -> issue.getId().equals(smallestIssueId));

        // 나머지 이슈들의 사용자 리스트 및 공감수 병합
        for (Issue issue : otherIssues) {
            // 사용자 리스트 병합
            List<UserIssue> userIssues = userIssueRepository.findByIssueId(issue.getId()); // 이미 한 번 합쳐진 이슈의 경우 여러 유저 존재
            for (UserIssue userIssue : userIssues) {
                for (Comment comment : issue.getComments()) {
                    comment.mergeComment(smallestIssue); // 이슈의 댓글을 기준 이슈의 댓글로 병합
                }
                userIssue.setIssue(smallestIssue); // 사용자-이슈 관계를 기준 이슈로 업데이트
            }

            // 공감수 병합
            smallestIssue.sync(mergeIssueResDto);

            // 병합 완료 후 나머지 이슈 삭제
            issueRepository.delete(issue);
        }

        // 업데이트된 기준 이슈 저장
        issueRepository.save(smallestIssue);
    }
}
