package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.MedicalSpecialty;
import com.dinlurceis.smartmed.domain.S3Folder;
import com.dinlurceis.smartmed.domain.UserRole;
import com.dinlurceis.smartmed.dto.request.ChangePasswordRequest;
import com.dinlurceis.smartmed.dto.request.SpecialtyUpdateRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.request.UserUpdateRequest;
import com.dinlurceis.smartmed.dto.response.DoctorResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.UserMapper;
import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.Cart;
import com.dinlurceis.smartmed.model.CartItem;
import com.dinlurceis.smartmed.model.User;
import com.dinlurceis.smartmed.repository.UserRepository;
import com.dinlurceis.smartmed.service.AWSS3Service;
import com.dinlurceis.smartmed.service.UserService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AWSS3Service awss3Service;

    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserCreateRequest request, UserRole role) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(role.name());
        user.setRoles(roles);

        user.setActive(true);

        HashSet<CartItem> cartItems = new HashSet<>();
        Cart cart = Cart.builder()
                .cartItems(cartItems)
                .user(user)
                .build();
        user.setCart(cart);

        Set<Address> addresses = new HashSet<>();
        user.setAddresses(addresses);

        userRepository.save(user);
        log.info(user.getRoles().toString());

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request, MultipartFile avatarFile) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        if (!currentUser.getEmail().equals(email)) {
            throw new AppException(ErrorCode.CANNOT_UPDATE_OTHER_USERS);
        }

        currentUser.setFullName(request.getFullName());
        currentUser.setPhoneNumber(request.getPhoneNumber());

//        if (request.getAddresses() != null) {
//            // Xoá danh sách cũ đúng chuẩn Hibernate
//            currentUser.getAddresses().clear();
//
//            // Gán user cho từng address nếu có quan hệ bidirectional
//            for (Address address : request.getAddresses()) {
//                address.setUser(currentUser); // cần nếu dùng mappedBy = "user"
//            }
//
//            currentUser.getAddresses().addAll(request.getAddresses());
//        }


        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String oldAvatarUrl = currentUser.getImageUrl();

                // Upload avatar mới
                String newAvatarUrl = awss3Service.uploadFile(avatarFile, S3Folder.AVATAR.getFolderName());
                currentUser.setImageUrl(newAvatarUrl);

                // Xóa avatar cũ nếu tồn tại
                if (oldAvatarUrl != null && !oldAvatarUrl.isEmpty()) {
                    awss3Service.deleteFile(oldAvatarUrl);
                }
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }
        
        User updatedUser = userRepository.save(currentUser);
        
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public UserResponse getMyInfo() {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));

        User user = userRepository.findByEmail(email);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public UserResponse changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public PageResponse<List<DoctorResponse>> getDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> doctors = userRepository.findAllByMedicalSpecialtyIsNotNull(pageable);
        return PageResponse.<List<DoctorResponse>>builder()
                .items(doctors.stream().map(userMapper::toDoctorResponse).toList())
                .pageSize(size)
                .currentPage(page)
                .totalElements(doctors.getTotalElements())
                .totalPages(doctors.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<List<DoctorResponse>> getDoctorsBySpecialty(int page, int size, String specialty) {
        Pageable pageable = PageRequest.of(page - 1, size);
        MedicalSpecialty medicalSpecialty = MedicalSpecialty.fromName(specialty);
        Page<User> doctors = userRepository
                .findAllByMedicalSpecialtyIsNotNullAndMedicalSpecialty(medicalSpecialty, pageable);
        return PageResponse.<List<DoctorResponse>>builder()
                .items(doctors.stream().map(userMapper::toDoctorResponse).toList())
                .pageSize(size)
                .currentPage(page)
                .totalElements(doctors.getTotalElements())
                .totalPages(doctors.getTotalPages())
                .build();
    }

    @Override
    public DoctorResponse updateSpecialty(SpecialtyUpdateRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User doctor = userRepository.findByEmail(email);
        if (doctor == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);

        doctor.setMedicalSpecialty(MedicalSpecialty.fromName(request.getSpecialty()));

        return userMapper.toDoctorResponse(userRepository.save(doctor));
    }
}