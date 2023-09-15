package com.omerfurkan.studentforum.services;



import com.omerfurkan.studentforum.entities.Email;
import com.omerfurkan.studentforum.repositories.EmailRepository;
import com.omerfurkan.studentforum.repositories.UserRepository;
import com.omerfurkan.studentforum.requests.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@AllArgsConstructor
@NoArgsConstructor
@Service
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

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);


    // TODO: ASYNC process, custom error implementation
    public ResponseEntity<String> sendSimpleEmail(@RequestBody EmailRequest simpleEmailRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        String subject = simpleEmailRequest.getSubject();
        String body = simpleEmailRequest.getBody();
        List<String> recipients = simpleEmailRequest.getRecipients();
        String template = simpleEmailRequest.getTemplateName();

        message.setFrom(from);
        message.setSubject(subject);
        message.setText(body);

        for (String recipient : recipients) {
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
        }
        ;
        return ResponseEntity.ok("Simple emails sent successfully");


    }

    //TODO: should create a new custom error class with status code and message
    public ResponseEntity<String> sendHtmlEmail(EmailRequest templateEmailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(from));
        message.setSubject(templateEmailRequest.getSubject());
        List<String> recipients = templateEmailRequest.getRecipients();
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(String.join(",", recipients)));

        for (String recipient : recipients) {
            Context context = new Context();
            context.setVariables(templateEmailRequest.getVariablesOfRecipient(recipient));
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
                logger.error("Error sending email to {}", recipient);
                return ResponseEntity.internalServerError().body("Error");

            }
        }
        return ResponseEntity.ok("Html emails sent successfully");
    }

    public ResponseEntity<String> checkEduMailAndSendHtml(EmailRequest templateEmailRequest) throws MessagingException {
        List<String> recipients = templateEmailRequest.getRecipients();
        List<String> validRecipients = recipients.stream().filter(recipient ->
            recipient.endsWith(".edu.tr") && checkMailFormat(recipient)).toList();
        if (validRecipients.size() == 0) {
            return ResponseEntity.badRequest().body("No valid recipients");
        }
        List<Map<String, String>> validVariables =
            templateEmailRequest.getVariables().stream().filter(map -> validRecipients.contains(map.get("recipient"))).toList();

        EmailRequest newTemplateEmailRequest = new EmailRequest();
        newTemplateEmailRequest.setRecipients(validRecipients)
            .setSubject(templateEmailRequest.getSubject())
            .setTemplateName(templateEmailRequest.getTemplateName())
            .setVariables(validVariables);
        return sendHtmlEmail(newTemplateEmailRequest);

    }

    public ResponseEntity<String> checkEduMailAndSendSimple(EmailRequest emailRequest) {
        List<String> recipients = emailRequest.getRecipients();
        List<String> validRecipients = recipients.stream().filter(recipient ->
            recipient.endsWith(".edu.tr") && checkMailFormat(recipient)).toList();
        if (validRecipients.size() == 0) {
            return ResponseEntity.badRequest().body("No valid recipients");
        }
        EmailRequest newEmailRequest = new EmailRequest();
        newEmailRequest.setRecipients(validRecipients)
            .setSubject(emailRequest.getSubject())
            .setBody(emailRequest.getBody());
        return sendSimpleEmail(newEmailRequest);
    }

    public boolean checkMailFormat(String recipient) {
        return recipient.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }


}

