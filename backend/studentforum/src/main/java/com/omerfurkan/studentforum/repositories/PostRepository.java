package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Optional<Long> userId);
}
