package com.omerfurkan.studentforum.requests;

import lombok.Data;

@Data
public class SearchHistoryCreateRequest {
    private Long userId;
    private String searchTerm;
}
