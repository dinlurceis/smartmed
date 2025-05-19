package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.MedicineCreateRequest;
import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import com.dinlurceis.smartmed.dto.response.MedicineResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MedicineService {
    PageResponse<List<MedicineResponse>> getAllMedicine(int page, int size);
    MedicineResponse createMedicine(MedicineCreateRequest request, MultipartFile file);

    MedicineResponse getMedicineById(Long medicineId);

    MedicineResponse updateMedicine(Long medicineId, MedicineCreateRequest request, MultipartFile file);

    void deleteMedicine(Long medicineId);

    PageResponse<List<MedicineResponse>> getMedicineByCategory(int page, int size, Long categoryId);

    PageResponse<List<MedicineResponse>> getMedicines(PageRequestDTO request);
}
