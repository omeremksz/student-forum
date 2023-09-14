package com.omerfurkan.studentforum.requests;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostUpdateRequest {
    private Long postPreferencesId;
    private String contentText;
    private LocalDateTime updatedTime;
}
