package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.DiseaseRequest;
import com.dinlurceis.smartmed.dto.response.DiseaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiseaseService {
    List<DiseaseResponse> getDiseases(int page, int size);

    DiseaseResponse getDiseaseById(Long id);

    DiseaseResponse createDisease(DiseaseRequest request, MultipartFile image);

    DiseaseResponse updateDisease(Long id, DiseaseRequest request, MultipartFile file);

    void deleteDisease(Long id);
}
