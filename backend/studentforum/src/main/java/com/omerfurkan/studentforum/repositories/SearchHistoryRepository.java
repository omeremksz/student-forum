package com.omerfurkan.studentforum.repositories;

import com.omerfurkan.studentforum.entities.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
}
