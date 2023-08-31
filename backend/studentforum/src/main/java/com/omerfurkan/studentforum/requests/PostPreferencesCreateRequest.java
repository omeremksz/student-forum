package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class PostPreferencesCreateRequest {
    private String interactionEnvironment;
    private String postCategory;
}
