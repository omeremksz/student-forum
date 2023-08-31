package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class PostCreateRequest {
    private Long userId;
    private Long postPreferencesId;
    private String contentText;
    private String contentPictureURL;
}
