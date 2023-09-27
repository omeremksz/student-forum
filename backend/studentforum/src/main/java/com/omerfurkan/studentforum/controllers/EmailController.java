package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.requests.EmailRequest;
import com.omerfurkan.studentforum.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    private EmailService emailService;


    @PostMapping("/simple")
    public ResponseEntity<String> sendSimpleEmail(@RequestBody EmailRequest simpleEmailRequest) {
        return emailService.sendSimpleEmail(simpleEmailRequest);
    }

    @PostMapping("/html")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody EmailRequest htmlEmailRequest) throws MessagingException {
        return emailService.sendHtmlEmail(htmlEmailRequest);
    }

    @PostMapping("/verification")
    public ResponseEntity<String> sendVerificationCodeEmail(@RequestBody EmailRequest verificationEmailRequest) throws MessagingException {
        return emailService.sendVerificationMail(verificationEmailRequest);
    }


}
