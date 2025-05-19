package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.CategoryCreateRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.CategoryResponse;
import com.dinlurceis.smartmed.model.Category;
import com.dinlurceis.smartmed.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> addCategory(
            @RequestBody CategoryCreateRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Add category successfully")
                .result(categoryService.createCategory(request))
                .build();
    }
    @GetMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> getCategoryById(
            @PathVariable("categoryId") Long categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Category by id")
                .result(categoryService.getCategoryById(categoryId))
                .build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ApiResponse<?> deleteCategory(
            @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<Void>builder()
                .message("Delete category successfully")
                .build();
    }
    @GetMapping("/list")
    public ApiResponse<List<CategoryResponse>> getCategoriesByParent(@RequestParam(required = false) Long parentId) {
        List<CategoryResponse> categories;
        String message;

        if (parentId == null) {
            categories = categoryService.getRootCategories();  // categories không có parent
            message = "Get all root categories";
        } else {
            categories = categoryService.getCategoriesByParentId(parentId);
            message = "Get all subcategories of parent id: " + parentId;
        }

        return ApiResponse.<List<CategoryResponse>>builder()
                .message(message)
                .result(categories)
                .build();
    }
    @GetMapping("/list-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<CategoryResponse>> getAllCategory() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .message("Get all category")
                .result(categoryService.getAllCategories())
                .build();
    }

}