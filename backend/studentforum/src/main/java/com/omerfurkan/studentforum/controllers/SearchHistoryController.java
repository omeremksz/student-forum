package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.SearchHistory;
import com.omerfurkan.studentforum.requests.SearchHistoryCreateRequest;
import com.omerfurkan.studentforum.requests.SearchHistoryUpdateRequest;
import com.omerfurkan.studentforum.services.SearchHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search-history")
public class SearchHistoryController {
    private SearchHistoryService searchHistoryService;

    public SearchHistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping
    public List<SearchHistory> getAllSearchHistory() {
        return searchHistoryService.getAllSearchHistory();
    }

    @GetMapping("/{searchHistoryId}")
    public SearchHistory getSearchHistoryById(@PathVariable Long searchHistoryId) {
        return searchHistoryService.getSearchHistoryById(searchHistoryId);
    }

    @PostMapping
    public SearchHistory createNewSearchHistory(@RequestBody SearchHistoryCreateRequest searchHistoryCreateRequest) {
        return searchHistoryService.createNewSearchHistory(searchHistoryCreateRequest);
    }

    @PutMapping("/{searchHistoryId}")
    public SearchHistory updateSearchHistoryById(@PathVariable Long searchHistoryId,
                                             @RequestBody SearchHistoryUpdateRequest searchHistoryUpdateRequest) {
        return searchHistoryService.updateSearchHistoryById(searchHistoryId, searchHistoryUpdateRequest);
    }

    @DeleteMapping("/{searchHistoryId}")
    public void deleteSearchHistoryById(@PathVariable Long searchHistoryId) {
        searchHistoryService.deleteSearchHistoryById(searchHistoryId);
    }
}
