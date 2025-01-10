package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.CommentReqDto;
import com.caramelpopcorn.campusconnect.dto.EmpathyDto;
import com.caramelpopcorn.campusconnect.global.code.SuccessCode;
import com.caramelpopcorn.campusconnect.service.CommentService;
import com.caramelpopcorn.campusconnect.service.EmpathyService;
import com.caramelpopcorn.campusconnect.util.ApiResponseUtil;
import com.caramelpopcorn.campusconnect.util.Api_Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empathy")
@RequiredArgsConstructor
public class EmpathyController {
    private final EmpathyService empathyService;
    @PostMapping("/create")
    public <T> ResponseEntity<Api_Response<T>> createEmpathy(@RequestBody EmpathyDto empathyDto) {
        empathyService.createEmpathy(empathyDto);
        return ApiResponseUtil.createSuccessResponse(SuccessCode.INSERT_SUCCESS, null);
    }
}
