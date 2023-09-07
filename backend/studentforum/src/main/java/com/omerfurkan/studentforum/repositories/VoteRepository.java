package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository  extends JpaRepository<Vote, Long> {
    List<Vote> findByUserIdAndPostId(Long userId, Long postId);

    List<Vote> findByUserId(Long userId);

    List<Vote> findByPostId(Long postId);

    List<Vote> findByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId);

    List<Vote> findByCommentId(Long commentId);
}
