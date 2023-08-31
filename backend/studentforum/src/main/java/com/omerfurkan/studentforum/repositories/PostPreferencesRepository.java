package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.PostPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostPreferencesRepository extends JpaRepository<PostPreferences, Long> {
}
