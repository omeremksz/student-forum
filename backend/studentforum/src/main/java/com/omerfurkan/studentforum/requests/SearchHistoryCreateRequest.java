package com.omerfurkan.studentforum.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchHistoryCreateRequest {
    private Long userId;
    private String searchTerm;
}
