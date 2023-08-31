package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private Long userId;
    private Long postId;
    private String contentText;
}
