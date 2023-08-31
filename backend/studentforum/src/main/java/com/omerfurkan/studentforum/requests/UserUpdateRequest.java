package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String userName;
    private String password;
    private String email;
}
