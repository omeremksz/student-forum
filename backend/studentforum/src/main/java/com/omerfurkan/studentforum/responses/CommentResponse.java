package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private String userName;
    private Long priorCommentId;
    private String contentText;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public CommentResponse(Comment entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
        this.userName = entity.getUser().getUserName();
        this.priorCommentId = entity.getPriorCommentId();
        this.contentText = entity.getContentText();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();
    }
}
