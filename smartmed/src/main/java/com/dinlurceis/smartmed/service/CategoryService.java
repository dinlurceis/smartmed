package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.CategoryCreateRequest;
import com.dinlurceis.smartmed.dto.response.CategoryResponse;
import com.dinlurceis.smartmed.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest request);

    CategoryResponse getCategoryById(Long categoryId);

    void deleteCategory(Long categoryId);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getRootCategories();

    List<CategoryResponse> getCategoriesByParentId(Long parentId);
}
