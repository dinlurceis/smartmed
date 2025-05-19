package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import com.dinlurceis.smartmed.dto.request.PharmacistRequest;
import com.dinlurceis.smartmed.dto.response.MedicineResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.PharmacistResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.PharmacistMapper;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.Pharmacist;
import com.dinlurceis.smartmed.repository.PharmacistRepository;
import com.dinlurceis.smartmed.service.PharmacistService;
import com.dinlurceis.smartmed.util.PaginationUtils;
import com.dinlurceis.smartmed.util.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacistServiceImpl implements PharmacistService {
    private final PharmacistRepository pharmacistRepository;
    private final PharmacistMapper pharmacistMapper;

    @Override
    public PageResponse<List<PharmacistResponse>> getPharmacists(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Pharmacist> pharmacists = pharmacistRepository.findAll(pageable);

        return PageResponse.<List<PharmacistResponse>>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(pharmacists.getTotalElements())
                .totalPages(pharmacists.getTotalPages())
                .items(pharmacists.stream().map(pharmacistMapper::toPharmacistResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PharmacistResponse getPharmacistById(Long id) {
        Pharmacist pharmacist = pharmacistRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PHARMACIST_NOT_FOUND));
        return pharmacistMapper.toPharmacistResponse(pharmacist);
    }

    @Override
    public PharmacistResponse createPharmacist(PharmacistRequest request) {
        // ktra email và sdt đã tồn tại ch
        if (pharmacistRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.PHARMACIST_EMAIL_ALREADY_EXISTS);
        }
        if (pharmacistRepository.existsByPhone(request.getPhone())) {
            throw new AppException(ErrorCode.PHARMACIST_PHONE_ALREADY_EXISTS);
        }

        Pharmacist pharmacist = pharmacistMapper.toPharmacist(request);
        Pharmacist savedPharmacist = pharmacistRepository.save(pharmacist);
        return pharmacistMapper.toPharmacistResponse(savedPharmacist);
    }

    @Override
    public PharmacistResponse updatePharmacist(Long id, PharmacistRequest request) {
        Pharmacist pharmacist = pharmacistRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PHARMACIST_NOT_FOUND));

        // Kiểm tra email và số điện thoại mới có bị trùng không
        if (!pharmacist.getEmail().equals(request.getEmail()) 
                && pharmacistRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.PHARMACIST_EMAIL_ALREADY_EXISTS);
        }
        if (!pharmacist.getPhone().equals(request.getPhone()) 
                && pharmacistRepository.existsByPhone(request.getPhone())) {
            throw new AppException(ErrorCode.PHARMACIST_PHONE_ALREADY_EXISTS);
        }

        pharmacist.setName(request.getName());
        pharmacist.setPhone(request.getPhone());
        pharmacist.setEmail(request.getEmail());
        pharmacist.setLicenseNumber(request.getLicenseNumber());

        Pharmacist updatedPharmacist = pharmacistRepository.save(pharmacist);
        return pharmacistMapper.toPharmacistResponse(updatedPharmacist);
    }

    @Override
    public void deletePharmacist(Long id) {
        Pharmacist pharmacist = pharmacistRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PHARMACIST_NOT_FOUND));
        
        pharmacistRepository.delete(pharmacist);
    }
}