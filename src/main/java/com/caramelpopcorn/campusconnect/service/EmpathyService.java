package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.dto.EmpathyDto;
import com.caramelpopcorn.campusconnect.entity.Empathy;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.entity.User;
import com.caramelpopcorn.campusconnect.repository.EmpathyRepository;
import com.caramelpopcorn.campusconnect.repository.IssueRepository;
import com.caramelpopcorn.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpathyService {
    private final IssueRepository issueRepository;
    private final EmpathyRepository empathyRepository;
    private final UserRepository userRepository;

    public Empathy createEmpathy(EmpathyDto empathyDto) {
        Issue issue = issueRepository.findById(empathyDto.getBoard_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이슈가 존재하지 않습니다."));

        User user = userRepository.findById(empathyDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다."));

        Empathy existingEmpathy = empathyRepository.findByIssueAndUser(issue, user);
        if (existingEmpathy != null) {
            empathyRepository.delete(existingEmpathy);
            return existingEmpathy;
        } else {
            Empathy empathy = Empathy.builder()
                    .issue(issue)
                    .user(user)
                    .build();
            return empathyRepository.save(empathy);
        }
    }
    public Long getEmpathyCountByIssueId(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이슈가 존재하지 않습니다."));
        return empathyRepository.countByIssue(issue);
    }
}
