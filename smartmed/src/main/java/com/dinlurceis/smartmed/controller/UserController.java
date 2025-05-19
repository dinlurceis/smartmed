package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.domain.UserRole;
import com.dinlurceis.smartmed.dto.request.ChangePasswordRequest;
import com.dinlurceis.smartmed.dto.request.SpecialtyUpdateRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.request.UserUpdateRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.DoctorResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.dinlurceis.smartmed.service.SecurityService;
import com.dinlurceis.smartmed.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> getUser (@PathVariable Long userId) {
        return ApiResponse.<UserResponse>builder()
                .message("Get user successfully")
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        authentication.getAuthorities().forEach(grantedAuthority ->
                log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .message("Get users successfully")
                .result(userService.getUsers())
                .build();
    }

    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestPart("request") UserUpdateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile avatarFile
    ) {
        UserResponse updatedUser = userService.updateUser(userId, request, avatarFile);
        return ApiResponse.<UserResponse>builder()
                .message("Update user successfully")
                .result(updatedUser)
                .build();
    }

    @GetMapping("/my-info")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .message("Get my info")
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> deleteUser(@PathVariable Long userId) {
        return ApiResponse.<UserResponse>builder()
                .message("Delete user successfully")
                .build();
    }

    @PatchMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<UserResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Change password successfully")
                .result(userService.changePassword(request))
                .build();
    }

    @PostMapping("/create-doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> createDoctor(@RequestBody UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Create doctor successfully")
                .result(userService.createUser(request, UserRole.DOCTOR))
                .build();
    }

    @GetMapping("/doctors")
    public ApiResponse<PageResponse<List<DoctorResponse>>> getDoctors(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<List<DoctorResponse>>>builder()
                .message("Get doctor lists successfully")
                .result(userService.getDoctors(page, size))
                .build();
    }

    @GetMapping("/doctors/specialty")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<PageResponse<List<DoctorResponse>>> getDoctorsBySpecialty(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam String specialty) {
        return ApiResponse.<PageResponse<List<DoctorResponse>>>builder()
                .message("Get doctor lists successfully")
                .result(userService.getDoctorsBySpecialty(page, size, specialty))
                .build();
    }

    @PatchMapping("/doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DoctorResponse> updateSpecialty(@RequestBody SpecialtyUpdateRequest request) {
        return ApiResponse.<DoctorResponse>builder()
                .message("Update specialty successfully")
                .result(userService.updateSpecialty(request))
                .build();
    }
}