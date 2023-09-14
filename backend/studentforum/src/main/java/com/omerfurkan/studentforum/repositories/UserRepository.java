package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    User findByEducationalEmail(String educationalEmail);
}
