package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class VoteCreateRequest {
    private Long userId;
    private Long postId;
    private Long commentId;
    private Boolean isUpVote;
}


