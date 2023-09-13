package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Profile;
import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String educationalEmail;
    private String email;
    private String profilePictureURL;
    private String about;

    public ProfileResponse(Profile entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.userName = entity.getUser().getUserName();
        this.educationalEmail = entity.getUser().getEducationalEmail();
        this.email = entity.getEmail();
        this.profilePictureURL = entity.getProfilePictureURL();
        this.about = entity.getAbout();
    }
}
