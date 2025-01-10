package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.CommentReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.entity.Comment;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.service.CommentService;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import com.caramelpopcorn.campusconnect.util.Api_Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/create")
    public <T> ResponseEntity<Api_Response<T>> createComment(@RequestBody CommentReqDto commentReqDto) {
        commentService.createComment(commentReqDto);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<Api_Response<List<Comment>>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comment> comments = commentService.getCommentsByIssueId(issueId);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.FETCH_SUCCESS, comments);
    }
}
