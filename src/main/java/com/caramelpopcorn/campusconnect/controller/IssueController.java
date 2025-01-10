package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.util.Api_Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueController {
    @PostMapping
    public <T> ResponseEntity<Api_Response<T>> createIssue(@RequestBody IssueReqDto issueReqDto) {
        return null;
    }
}
