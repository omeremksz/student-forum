package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
