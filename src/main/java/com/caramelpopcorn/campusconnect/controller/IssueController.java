package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.AiReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueResDto;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.global.State;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.service.IssueService;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import com.caramelpopcorn.campusconnect.util.Api_Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/create")
    public <T> ResponseEntity<Api_Response<T>> createIssue(@RequestBody IssueReqDto issueReqDto) {
        issueService.createIssue(issueReqDto, "Yoo");
        return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
    }

    @PostMapping("/priority")
    public <T> ResponseEntity<Api_Response<T>> aiPriority(@RequestBody AiReqDto aiReqDto) {
        // AI 관련 로직(작성중)
        return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
    }

    @GetMapping("/all")
    public ResponseEntity<Api_Response<List<IssueResDto>>> getAllIssues() {
        List<IssueResDto> issueResDto = issueService.getAllIssues();
        return ApiResponseUtil.createSuccessResponse(SuccessCode.FETCH_SUCCESS, issueResDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Api_Response<Issue>> getIssueById(@PathVariable Long id) {
        Issue issue = issueService.getIssueById(id);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.FETCH_SUCCESS, issue);
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Api_Response<Issue>> updateState(@PathVariable Long id, @RequestParam State state) {
        Issue updatedIssue = issueService.updateIssueState(id, state);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.UPDATE_SUCCESS, updatedIssue);
    }
}
