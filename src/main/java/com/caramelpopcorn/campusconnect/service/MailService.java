package com.caramelpopcorn.campusconnect.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "jespri1379@gmail.com";
    private final HttpSession httpSession;

    public static int createNumber(){
        return (int)(Math.random() * (90000)) + 100000;
    }

    public MimeMessage CreateMail(String mail, int number) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증");
        String body = "";
        body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";
        message.setText(body,"UTF-8", "html");

        return message;
    }

    public void sendMail(String mail) throws Exception{
        if (!mail.endsWith("kangnam.ac.kr")){
            throw new IllegalArgumentException("강남대학교 이메일로만 인증 가능합니다. 다시 시도해주세요.");
        }
        int number = createNumber();

        MimeMessage message = CreateMail(mail, number);

        httpSession.setAttribute("timer", System.currentTimeMillis() + 90 * 1000);
        httpSession.setAttribute("number", number);

        javaMailSender.send(message);

    }

    public void confirmNumber(String enteredNumber) {
        Long timer = (Long) httpSession.getAttribute("timer");
        int number = (int) httpSession.getAttribute("number");

        if (System.currentTimeMillis() > timer){
            httpSession.removeAttribute("timer");
            httpSession.removeAttribute("number");
            throw new IllegalArgumentException("인증 번호가 만료되었습니다. 다시 시도해주세요");
        }else if (number != Integer.parseInt(enteredNumber)){
            throw new IllegalArgumentException("인증 번호가 다릅니다. 다시 시도해주세요.");
        }else{
            //유저 역할 설정
            httpSession.removeAttribute("timer");
            httpSession.removeAttribute("number");
        }
    }
}