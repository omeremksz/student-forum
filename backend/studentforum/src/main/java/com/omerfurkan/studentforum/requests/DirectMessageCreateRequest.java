package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class DirectMessageCreateRequest {
    private Long senderId;
    private Long receiverId;
    private String contentText;
}
