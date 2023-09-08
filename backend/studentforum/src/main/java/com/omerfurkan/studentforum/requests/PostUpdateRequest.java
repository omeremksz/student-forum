package com.omerfurkan.studentforum.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostUpdateRequest {
    private Long postPreferencesId;
    private String contentText;
    private LocalDateTime updatedTime;
}
