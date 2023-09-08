package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
}
