package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.S3Folder;
import com.dinlurceis.smartmed.dto.request.MedicineCreateRequest;
import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import com.dinlurceis.smartmed.dto.response.MedicineResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.MedicineMapper;
import com.dinlurceis.smartmed.model.Category;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.repository.CategoryRepository;
import com.dinlurceis.smartmed.repository.MedicineRepository;
import com.dinlurceis.smartmed.service.AWSS3Service;
import com.dinlurceis.smartmed.service.MedicineService;
import com.dinlurceis.smartmed.util.PaginationUtils;
import com.dinlurceis.smartmed.util.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.data.domain.PageImpl;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    private final MedicineMapper medicineMapper;
    
    private final AWSS3Service awss3Service;

    private final CategoryRepository categoryRepository;

    @Override
    public PageResponse<List<MedicineResponse>> getAllMedicine(int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<Medicine> medicinePage=medicineRepository.findAll(pageable);
        return PageResponse.<List<MedicineResponse>>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(medicinePage.getTotalElements())
                .totalPages(medicinePage.getTotalPages())
                .items(medicinePage.stream().map(medicineMapper::toMedicineResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    public MedicineResponse createMedicine(MedicineCreateRequest request, MultipartFile file) {
        Medicine medicine = medicineMapper.toMedicine(request);

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = awss3Service.uploadFile(file, S3Folder.MEDICINE.getFolderName());
                medicine.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );
        medicine.setCategory(category);

        Medicine savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.toMedicineResponse(savedMedicine);
    }

    @Override
    public MedicineResponse getMedicineById(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(
                () -> new AppException(ErrorCode.MEDICINE_NOT_FOUND)
        );
        return medicineMapper.toMedicineResponse(medicine);
    }

    @Override
    public MedicineResponse updateMedicine(Long medicineId, MedicineCreateRequest request, MultipartFile file) {
        Medicine medicine = medicineMapper.toMedicine(request);
        medicine.setId(medicineId);

        if (file != null && !file.isEmpty()) {
            try {
                if (medicine.getImageUrl() != null && !medicine.getImageUrl().isEmpty())
                    awss3Service.deleteFile(medicine.getImageUrl());
                String imageUrl = awss3Service.uploadFile(file, S3Folder.MEDICINE.getFolderName());
                medicine.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );
        medicine.setCategory(category);

        Medicine savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.toMedicineResponse(savedMedicine);
    }

    @Override
    public void deleteMedicine(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }

    @Override
    public PageResponse<List<MedicineResponse>> getMedicineByCategory(int page, int size, Long categoryId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        List<Medicine> medicines = new ArrayList<>(category.getAllMedicines().stream().toList());
        medicines.sort(Comparator.comparing(Medicine::getName));

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = start + pageable.getPageSize();
        
        List<Medicine> pageContent = medicines.subList(start, end);
        
        Page<Medicine> medicinePage = new PageImpl<>(
            pageContent, 
            pageable, 
            medicines.size()
        );
        
        return PageResponse.<List<MedicineResponse>>builder()
            .items(medicinePage.getContent().stream()
                    .map(medicineMapper::toMedicineResponse)
                    .collect(Collectors.toList()))
            .totalPages(medicinePage.getTotalPages())
            .totalElements(medicinePage.getTotalElements())
            .currentPage(page)
            .pageSize(size)
            .build();
    }

    @Override
    public PageResponse<List<MedicineResponse>> getMedicines(PageRequestDTO request) {
        Pageable pageable = PaginationUtils.toPageable(request);
        SpecificationBuilder<Medicine> builder = new SpecificationBuilder<>();

        Specification<Medicine> spec = builder.build(
                request.getFilters(),
                request.getKeyword(),
                "name" // các field
        );

        Page<Medicine> medicines = medicineRepository.findAll(spec, pageable);

        List<MedicineResponse> data = medicines.getContent().stream()
                .map(medicineMapper::toMedicineResponse)
                .toList();

        return PageResponse.<List<MedicineResponse>>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalElements(medicines.getTotalElements())
                .totalPages(medicines.getTotalPages())
                .items(data)
                .build();
    }


}