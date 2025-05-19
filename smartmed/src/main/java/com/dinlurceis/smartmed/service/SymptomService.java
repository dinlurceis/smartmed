package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.dinlurceis.smartmed.dto.request.SymptomRequest;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.SymptomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SymptomService {
    PageResponse<List<SymptomResponse>> getSymptoms(int page, int size);

    SymptomResponse getSymptomById(Long id);

    SymptomResponse createSymptom(SymptomRequest request, MultipartFile file);

    SymptomResponse updateSymptom(Long id, SymptomRequest request, MultipartFile file);

    void deleteSymptom(Long id);

    List<SymptomResponse> getSymptomByBodyPart(BodyPart bodyPart);
}
