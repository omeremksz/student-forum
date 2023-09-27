package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.requests.EmailRequest;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserLoginRequest;
import com.omerfurkan.studentforum.requests.UserRegisterRequest;
import com.omerfurkan.studentforum.responses.AuthResponse;
import com.omerfurkan.studentforum.security.JwtTokenProvider;
import com.omerfurkan.studentforum.services.EmailService;
import com.omerfurkan.studentforum.services.UserService;
import jakarta.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    private EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);

        User user = userService.getUserByUserName(userLoginRequest.getUserName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setUserName(user.getUserName());
        authResponse.setAccessToken("Bearer " + jwtToken);

        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) throws MessagingException {
        if (userService.getUserByUserName(userRegisterRequest.getUserName()) != null ||
            userService.getUserByEducationalEmail(userRegisterRequest.getEducationalEmail()) != null) {
            return new ResponseEntity<>("Username or email already exists!", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();

            user.setUserName(userRegisterRequest.getUserName());
            user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setEducationalEmail(userRegisterRequest.getEducationalEmail());

            UserCreateRequest userCreateRequest = new UserCreateRequest(user);
            userService.createNewUserWithProfile(userCreateRequest);
            Map<String, String > mp = new HashMap<>();
            mp.put("recipient", userRegisterRequest.getEducationalEmail());
            mp.put("{username}", userRegisterRequest.getUserName());
            EmailRequest emailRequest = new EmailRequest().setRecipient(userRegisterRequest.getEducationalEmail())
                .setSubject("Student Forum Email Verification")
                    .setTemplateName("verification")
                        .setVariables(mp);
            emailService.checkEduMailAndSendHtml(emailRequest);

            return new ResponseEntity<>("User successfully registered!", HttpStatus.CREATED);
        }

    }
}
