package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    List<UserInteraction> findByUserIdAndPostIsNotNull(Long userId);

    List<UserInteraction> findByUserIdAndCommentIsNotNull(Long userId);
}
