package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class UserInteractionCreateRequest {
    private Long userId;
    private String interactionType;
    private Long referenceId;
    private String referenceType;
}
