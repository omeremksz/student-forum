package com.omerfurkan.studentforum.requests;

import com.omerfurkan.studentforum.entities.User;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String userName;
    private String password;
    private String educationalEmail;

    public UserCreateRequest(User entity) {
        this.userName = entity.getUserName();
        this.password = entity.getPassword();
        this.educationalEmail = entity.getEducationalEmail();
    }
}
