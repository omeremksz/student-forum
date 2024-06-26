package com.omerfurkan.studentforum.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "vote")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Comment comment;

    @Column(name = "is_upvote")
    private Boolean isUpvote;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
