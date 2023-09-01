package com.omerfurkan.studentforum.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "direct_message")
@Data
public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User receiver;

    @Lob
    @Column(name= "content_text", columnDefinition = "text")
    private String contentText;
    @Column(name = "sent_date")
    private LocalDateTime sentDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
