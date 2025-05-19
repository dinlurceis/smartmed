package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.CategoryCreateRequest;
import com.dinlurceis.smartmed.dto.response.CategoryResponse;
import com.dinlurceis.smartmed.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreateRequest request);
    CategoryResponse toCategoryResponse(Category category);
}
