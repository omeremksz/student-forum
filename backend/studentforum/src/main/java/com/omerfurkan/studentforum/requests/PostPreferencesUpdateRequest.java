package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class PostPreferencesUpdateRequest {
    private String interactionEnvironment;
    private String postCategory;
}
