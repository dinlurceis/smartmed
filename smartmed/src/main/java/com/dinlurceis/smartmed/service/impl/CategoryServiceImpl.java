package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.CategoryCreateRequest;
import com.dinlurceis.smartmed.dto.response.CategoryResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.CategoryMapper;
import com.dinlurceis.smartmed.model.Category;
import com.dinlurceis.smartmed.repository.CategoryRepository;
import com.dinlurceis.smartmed.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.toCategory(request);
        if (request.getParentCategoryId() != null && request.getParentCategoryId() != 0) {
            Category parentCategory = categoryRepository.findById(request.getParentCategoryId()).orElse(null);
            if (parentCategory == null) {
                throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
            }
            category.setParentCategory(parentCategory);
        }
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentCategoryIsNull().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> getCategoriesByParentId(Long parentId) {
        Category parentCategory = categoryRepository.findById(parentId).orElse(null);
        return categoryRepository.findByParentCategory(parentCategory).stream().map(categoryMapper::toCategoryResponse).toList();
    }
}
