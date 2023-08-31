package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
