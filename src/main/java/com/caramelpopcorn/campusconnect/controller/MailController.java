package com.caramelpopcorn.campusconnect.controller;

import com.caramelpopcorn.campusconnect.dto.MailDTO;
import com.caramelpopcorn.campusconnect.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    // 인증 번호 발송
    @PostMapping("/send")
    public synchronized ResponseEntity MailSend(@RequestBody MailDTO.send dto) {
        try{
            mailService.sendMail(dto.getMail());
            return ResponseEntity.ok("인증번호 발송. 1분 30초 안에 입력하시오.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 인증 번호 확인
    @PostMapping("/confirmNumber")
    public synchronized ResponseEntity ConfirmNumber(@RequestBody MailDTO.confirmNumber dto) {
        try{
            mailService.confirmNumber(dto.getEnteredNumber());
            return ResponseEntity.ok("이메일 인증이 성공했습니다.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
