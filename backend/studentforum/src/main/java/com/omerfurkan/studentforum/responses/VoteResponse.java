package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Vote;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VoteResponse {

    private Long id;
    private Long userId;
    private Long post_id;
    private Long comment_id;
    private Boolean isUpvote;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public VoteResponse(Vote entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.isUpvote = entity.getIsUpvote();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();

        if (entity.getPost() != null) {
            this.post_id = entity.getPost().getId();
        }

        if (entity.getComment() != null) {
            this.comment_id = entity.getComment().getId();
        }
    }
}
