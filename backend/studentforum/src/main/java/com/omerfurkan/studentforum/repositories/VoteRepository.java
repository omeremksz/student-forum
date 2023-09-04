package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository  extends JpaRepository<Vote, Long> {
}
