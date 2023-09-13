package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.services.PostService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class VoteResponse {

    private Long id;
    private Long userId;
    private Post post;
    private Comment comment;
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
            this.post = entity.getPost();
        }

        if (entity.getComment() != null) {
            this.comment = entity.getComment();
        }
    }
}
