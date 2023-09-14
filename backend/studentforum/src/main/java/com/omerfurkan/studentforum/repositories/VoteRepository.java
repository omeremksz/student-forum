package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUserIdAndPostId(Long userId, Long postId);

    List<Vote> findByUserId(Long userId);

    List<Vote> findByPostId(Long postId);

    List<Vote> findByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId);

    List<Vote> findByCommentId(Long commentId);
}
