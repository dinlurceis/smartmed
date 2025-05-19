package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.AuthenticationRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.request.UserUpdateRequest;
import com.dinlurceis.smartmed.dto.response.DoctorResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.dinlurceis.smartmed.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    UserResponse toUserResponse(User user);
    User toUser(AuthenticationRequest request);
    User toUser(UserUpdateRequest request);
    DoctorResponse toDoctorResponse(User user);
}
