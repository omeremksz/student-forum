package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Vote;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private Long commentId;
    private boolean isUpvote;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public VoteResponse(Vote entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.isUpvote = entity.getIsUpvote();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();

        if (entity.getPost() != null) {
            this.postId = entity.getPost().getId();
        }

        if (entity.getComment() != null) {
            this.commentId = entity.getComment().getId();
        }
    }
}
