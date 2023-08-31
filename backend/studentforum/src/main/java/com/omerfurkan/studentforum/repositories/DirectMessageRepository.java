package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
}
