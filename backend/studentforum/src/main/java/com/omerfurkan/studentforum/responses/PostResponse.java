package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String contentText;
    private String contentPictureURL;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.contentText = entity.getContentText();
        this.contentPictureURL = entity.getContentPictureURL();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();
    }
}
