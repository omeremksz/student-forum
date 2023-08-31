package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
}
