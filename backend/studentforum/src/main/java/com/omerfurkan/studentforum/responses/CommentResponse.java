package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.entities.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String contentText;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<VoteResponse> commentVotes;

    public CommentResponse(Comment entity, List<VoteResponse> commentVotes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.contentText = entity.getContentText();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();
        this.commentVotes = commentVotes;
    }
}
