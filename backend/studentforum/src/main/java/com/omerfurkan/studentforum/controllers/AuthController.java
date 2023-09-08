package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserLoginRequest;
import com.omerfurkan.studentforum.requests.UserRegisterRequest;
import com.omerfurkan.studentforum.security.JwtTokenProvider;
import com.omerfurkan.studentforum.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);

        return "Bearer " + jwtToken;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userService.getUserByUserName(userRegisterRequest.getUserName()) != null) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();

            user.setUserName(userRegisterRequest.getUserName());
            user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setEmail(userRegisterRequest.getEmail());

            UserCreateRequest userCreateRequest = new UserCreateRequest(user);
            userService.createNewUser(userCreateRequest);

            return new ResponseEntity<>("User successfully  registered!", HttpStatus.CREATED);
        }

    }
}
