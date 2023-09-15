package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.requests.EmailRequest;
import com.omerfurkan.studentforum.services.EmailService;
import com.omerfurkan.studentforum.utils.StuNetResponse;
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
    public ResponseEntity<StuNetResponse> sendSimpleEmail(@RequestBody EmailRequest simpleEmailRequest) {
        return emailService.sendSimpleEmail(simpleEmailRequest);
    }

    @PostMapping("/html")
    public ResponseEntity<StuNetResponse> sendHtmlEmail(@RequestBody EmailRequest htmlEmailRequest) throws MessagingException {
        return emailService.sendHtmlEmail(htmlEmailRequest);
    }

    @PostMapping("eduMail")
    public ResponseEntity<StuNetResponse> sendEduMail(@RequestBody EmailRequest eduMailRequest) {
        return emailService.checkEduMailAndSendSimple(eduMailRequest);
    }


}
