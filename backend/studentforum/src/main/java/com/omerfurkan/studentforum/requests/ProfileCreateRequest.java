package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class ProfileCreateRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureURL;
    private String about;
}
