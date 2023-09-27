package com.omerfurkan.studentforum.services;


import com.omerfurkan.studentforum.entities.Email;
import com.omerfurkan.studentforum.repositories.EmailRepository;
import com.omerfurkan.studentforum.repositories.UserRepository;
import com.omerfurkan.studentforum.requests.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;
import java.util.Random;


@AllArgsConstructor
@NoArgsConstructor
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;


    // TODO: ASYNC process, custom error implementation
    public ResponseEntity<String> sendSimpleEmail(@RequestBody EmailRequest simpleEmailRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        String subject = simpleEmailRequest.getSubject();
        String body = simpleEmailRequest.getBody();
        String recipient = simpleEmailRequest.getRecipient();
        String template = simpleEmailRequest.getTemplateName();

        message.setFrom(from);
        message.setSubject(subject);
        message.setText(body);


            try {
                message.setTo(recipient);
                mailSender.send(message);
                Email entity = new Email();
                entity.setRecipient(recipient)
                    .setUser(userRepository.findByEducationalEmail(recipient))
                    .setTemplate(template)
                    .setCreationDate(LocalDateTime.now());
                emailRepository.save(entity);

            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Error sending email to " + recipient);
            }

        return ResponseEntity.ok("Simple emails sent successfully");


    }

    //TODO: should create a new custom error class with status code and message
    public ResponseEntity<String> sendHtmlEmail(EmailRequest templateEmailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(from));
        message.setSubject(templateEmailRequest.getSubject());
        String recipient = templateEmailRequest.getRecipient();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));

        {
            Context context = new Context();

            context.setVariables(templateEmailRequest.getObjectVariables());
            context.setVariable("username", recipient);
            String html = templateEngine.process(templateEmailRequest.getTemplateName(), context);
            try {
                message.setContent(html, "text/html; charset=utf-8");
                mailSender.send(message);
                Email entity = new Email();
                entity.setRecipient(recipient)
                    .setUser(userRepository.findByEducationalEmail(recipient))
                    .setTemplate(templateEmailRequest.getTemplateName())
                    .setCreationDate(LocalDateTime.now());
                emailRepository.save(entity);

            } catch (MessagingException e) {
                log.error("Error sending email to {}", recipient);
                return ResponseEntity.internalServerError().body("Error");

            }
        }
        return ResponseEntity.ok("Html emails sent successfully");
    }
    public ResponseEntity<String> sendVerificationMail(EmailRequest verificationEmailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(from));
        message.setSubject(verificationEmailRequest.getSubject());
        String recipient = verificationEmailRequest.getRecipient();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));

        {
            Context context = new Context();

            for (Map.Entry<String, String> entry : verificationEmailRequest.getVariables().entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
            String html = templateEngine.process(verificationEmailRequest.getTemplateName(), context);
            try {
                message.setContent(html, "text/html; charset=utf-8");
                mailSender.send(message);
                Email entity = new Email();
                entity.setRecipient(recipient)
                    .setUser(userRepository.findByEducationalEmail(recipient))
                    .setTemplate(verificationEmailRequest.getTemplateName())
                    .setCreationDate(LocalDateTime.now());
                emailRepository.save(entity);

            } catch (MessagingException e) {
                log.error("Error sending email to {}", recipient);
                return ResponseEntity.internalServerError().body("Error");

            }
        }
        return ResponseEntity.ok("Html emails sent successfully");
    }

    public  ResponseEntity<String> checkEduMailAndSendVerificationCode(EmailRequest verificationCodeEmailRequest) {
        String recipient = verificationCodeEmailRequest.getRecipient();
        if (!recipient.endsWith(".edu.tr") || !checkMailFormat(recipient)) {
            log.error("No valid recipient {}", recipient);
            return ResponseEntity.badRequest().body("No valid recipients");
        }
        Random random = new Random();
        String randomCode = String.format("%06d",random.nextInt(0, 999999));

        EmailRequest newEmailRequest = new EmailRequest();
        newEmailRequest.setRecipient(recipient)
            .setSubject("Student Forum Verification Code")
            .setBody("Your verification code is: " + randomCode)
            .setTemplateName("verification");
        return sendSimpleEmail(newEmailRequest);
    }
    public ResponseEntity<String> checkEduMailAndSendHtml(EmailRequest templateEmailRequest) throws MessagingException {
        String recipient = templateEmailRequest.getRecipient();
        if (!recipient.endsWith(".edu.tr") || !checkMailFormat(recipient)) {
            log.error("No valid recipient {}", recipient);
            return ResponseEntity.badRequest().body("No valid recipients");
        }


        EmailRequest newTemplateEmailRequest = new EmailRequest();
        newTemplateEmailRequest.setRecipient(recipient)
            .setSubject(templateEmailRequest.getSubject())
            .setTemplateName(templateEmailRequest.getTemplateName())
            .setVariables(templateEmailRequest.getVariables());
        return sendHtmlEmail(newTemplateEmailRequest);

    }

    public ResponseEntity<String> checkEduMailAndSendSimple(EmailRequest emailRequest) {
        String recipient = emailRequest.getRecipient();
        if (!recipient.endsWith(".edu.tr") || !checkMailFormat(recipient)) {
            log.error("No valid recipient {}", recipient);
            return ResponseEntity.badRequest().body("No valid recipients");
        }
        EmailRequest newEmailRequest = new EmailRequest();
        newEmailRequest.setRecipient(recipient)
            .setSubject(emailRequest.getSubject())
            .setBody(emailRequest.getBody());
        return sendSimpleEmail(newEmailRequest);
    }

    public boolean checkMailFormat(String recipient) {
        return recipient.matches("^[A-Za-z0-9._+-]+@[A-Za-z]+\\.[A-Za-z.]*edu\\.tr$");
    }




}

