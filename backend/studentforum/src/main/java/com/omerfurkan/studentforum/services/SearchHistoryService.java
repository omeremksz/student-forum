package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.SearchHistory;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.SearchHistoryRepository;
import com.omerfurkan.studentforum.requests.SearchHistoryCreateRequest;
import com.omerfurkan.studentforum.requests.SearchHistoryUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryService {
    private SearchHistoryRepository searchHistoryRepository;
    private UserService userService;

    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository, UserService userService) {
        this.searchHistoryRepository = searchHistoryRepository;
        this.userService = userService;
    }

    public List<SearchHistory> getAllSearchHistory() {
        return searchHistoryRepository.findAll();
    }

    public SearchHistory getSearchHistoryById(Long searchHistoryId) {
        return searchHistoryRepository.findById(searchHistoryId).orElse(null);
    }

    public SearchHistory createNewSearchHistory(SearchHistoryCreateRequest searchHistoryCreateRequest) {
        User user = userService.getUserById(searchHistoryCreateRequest.getUserId());

        if (user == null) {
            return null;
        } else {
            SearchHistory searchHistoryToSave = new SearchHistory();

            searchHistoryToSave.setUser(user);
            searchHistoryToSave.setSearchTerm(searchHistoryCreateRequest.getSearchTerm());
            searchHistoryToSave.setSearchDate(LocalDateTime.now());

            return searchHistoryRepository.save(searchHistoryToSave);
        }
    }

    public SearchHistory updateSearchHistoryById(Long searchHistoryId, SearchHistoryUpdateRequest searchHistoryUpdateRequest) {
        Optional<SearchHistory> searchHistory = searchHistoryRepository.findById(searchHistoryId);

        if (searchHistory.isPresent()) {
            SearchHistory searchHistoryToUpdate = searchHistory.get();

            searchHistoryToUpdate.setSearchTerm(searchHistoryUpdateRequest.getSearchTerm());

            return searchHistoryRepository.save(searchHistoryToUpdate);
        } else {
            return null;
        }
    }

    public void deleteSearchHistoryById(Long searchHistoryId) {
        searchHistoryRepository.deleteById(searchHistoryId);
    }
}
