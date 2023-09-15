package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.UserInteraction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    List<UserInteraction> findByUserIdAndPostIsNotNull(Long userId);

    List<UserInteraction> findByUserIdAndCommentIsNotNull(Long userId);
}
