package com.dinlurceis.smartmed.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class PageRequestDTO {
    private int page = 1;
    private int size = 10;
    private String sortBy = "id";
    private String sortDirection = "asc";
    private String keyword;
    private Map<String, String> filters;
}
