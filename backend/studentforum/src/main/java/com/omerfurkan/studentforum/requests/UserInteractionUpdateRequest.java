package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class UserInteractionUpdateRequest {
    private Long userId;
    private String referenceType;
}
