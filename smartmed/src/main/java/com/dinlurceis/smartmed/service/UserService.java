package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.domain.UserRole;
import com.dinlurceis.smartmed.dto.request.ChangePasswordRequest;
import com.dinlurceis.smartmed.dto.request.SpecialtyUpdateRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.request.UserUpdateRequest;
import com.dinlurceis.smartmed.dto.response.DoctorResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.dinlurceis.smartmed.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request, UserRole role);
    UserResponse getUser(Long userId);
    List<UserResponse> getUsers();
    UserResponse updateUser(Long userId, UserUpdateRequest request, MultipartFile avatarFile);
    UserResponse getMyInfo();
    void deleteUser(Long userId);
    UserResponse changePassword(ChangePasswordRequest request);

    PageResponse<List<DoctorResponse>> getDoctors(int page, int size);

    PageResponse<List<DoctorResponse>> getDoctorsBySpecialty(int page, int size, String specialty);

    DoctorResponse updateSpecialty(SpecialtyUpdateRequest request);
}
