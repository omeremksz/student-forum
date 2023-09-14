package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Long userId);
}
