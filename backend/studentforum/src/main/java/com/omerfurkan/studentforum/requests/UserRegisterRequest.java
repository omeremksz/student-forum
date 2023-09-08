package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;
}
