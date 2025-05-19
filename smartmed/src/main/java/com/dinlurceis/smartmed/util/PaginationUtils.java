package com.dinlurceis.smartmed.util;

import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {

    public static Pageable toPageable(PageRequestDTO dto) {
        Sort.Direction direction = dto.getSortDirection().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return PageRequest.of(dto.getPage() - 1, dto.getSize(), Sort.by(direction, dto.getSortBy()));
    }
}
