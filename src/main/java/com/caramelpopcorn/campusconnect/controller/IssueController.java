package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.AiReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueResDto;
import com.caramelpopcorn.campusconnect.dto.MergeIssueResDto;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.global.State;
import com.caramelpopcorn.campusconnect.global.code.ErrorCode;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.service.IssueService;
import com.caramelpopcorn.campusconnect.service.UserIssueService;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import com.caramelpopcorn.campusconnect.util.Api_Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final UserIssueService userIssueService;

    @PostMapping("/create")
    public <T> ResponseEntity<Api_Response<T>> createIssue(@RequestBody IssueReqDto issueReqDto) {
        issueService.createIssue(issueReqDto, "Yoo");
        return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
    }

    @GetMapping("/priority")
    public ResponseEntity<?> aiPriority() throws IOException {
        File jsonFile = new File(getClass().getClassLoader().getResource("test-data.json").getFile());
        ObjectMapper objectMapper = new ObjectMapper();
        List<AiReqDto> aiReqDtoList = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, AiReqDto.class));

        System.out.println("test" + aiReqDtoList);
        // "전산" 카테고리인 게시물 필터링 후 상위 5개만 가져오기
        List<AiReqDto> recentPosts = aiReqDtoList.stream()
                .filter(post -> "전산".equals(post.getCategory())) // 카테고리가 '전산'인 게시물만 필터링
                .limit(5) // 5개만 가져오기
                .collect(Collectors.toList());

        // result만 반환
        return ResponseEntity.ok(recentPosts); // 상태 코드는 200 OK, 메시지는 default
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
    @PostMapping("/ai/mergeissue")
    public ResponseEntity<Api_Response<Issue>> mergeIssue(@RequestBody MergeIssueResDto mergeIssueResDto) {
        userIssueService.mergeIssues(mergeIssueResDto);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.UPDATE_SUCCESS, null);
    }

    @GetMapping("/test1")
    public ResponseEntity<Api_Response<List<Issue>>> getAllIssuesTest1() {
        return ApiResponseUtil.createSuccessResponse(SuccessCode.SELECT_SUCCESS, issueService.test1());
    }
    @GetMapping("/test2")
    public ResponseEntity<Api_Response<List<Issue>>> getAllIssuesTest2() {
        return ApiResponseUtil.createSuccessResponse(SuccessCode.SELECT_SUCCESS, issueService.test2());
    }
}
