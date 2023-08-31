package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String profilePictureURL;
    private String about;
}
