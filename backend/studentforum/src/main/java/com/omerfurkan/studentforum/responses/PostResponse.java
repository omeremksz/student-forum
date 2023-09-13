package com.omerfurkan.studentforum.responses;

import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.services.PostService;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String contentText;
    private String contentImageURL;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<VoteResponse> postVotes;

    public PostResponse(Post entity, List<VoteResponse> postVotes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.contentText = entity.getContentText();
        this.contentImageURL = entity.getContentImageURL();
        this.creationDate = entity.getCreationDate();
        this.updateDate = entity.getUpdateDate();
        this.postVotes = postVotes;
    }
}
