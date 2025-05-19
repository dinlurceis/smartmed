package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.dinlurceis.smartmed.domain.S3Folder;
import com.dinlurceis.smartmed.dto.request.SymptomRequest;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.SymptomResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.SymptomMapper;
import com.dinlurceis.smartmed.model.Symptom;
import com.dinlurceis.smartmed.repository.SymptomDiseaseRepository;
import com.dinlurceis.smartmed.repository.SymptomRepository;
import com.dinlurceis.smartmed.service.AWSS3Service;
import com.dinlurceis.smartmed.service.SymptomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SymptomServiceImpl implements SymptomService {
    private final SymptomRepository symptomRepository;
    private final SymptomMapper symptomMapper;
    private final AWSS3Service awss3Service;
    private final SymptomDiseaseRepository symptomDiseaseRepository;

    @Override
    public PageResponse<List<SymptomResponse>> getSymptoms(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Symptom> symptomPage = symptomRepository.findAll(pageable);
        return PageResponse.<List<SymptomResponse>>builder()
                .pageSize(size)
                .currentPage(page)
                .items(symptomPage.getContent().stream().map(symptomMapper::toSymptomResponse).collect(Collectors.toList()))
                .totalPages(symptomPage.getTotalPages())
                .totalElements(symptomPage.getTotalElements())
                .build();
    }

    @Override
    public SymptomResponse getSymptomById(Long id) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SYMPTOM_NOT_FOUND));
        return symptomMapper.toSymptomResponse(symptom);
    }

    @Override
    public SymptomResponse createSymptom(SymptomRequest request, MultipartFile imageFile) {
        if (symptomRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SYMPTOM_NAME_EXISTS);
        }

        Symptom symptom = Symptom.builder()
                .name(request.getName())
                .description(request.getDescription())
                .bodyPart(BodyPart.fromName(request.getBodyPart())).build();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = awss3Service.uploadFile(imageFile, S3Folder.SYMPTOMS.getFolderName());
                symptom.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        Symptom savedSymptom = symptomRepository.save(symptom);
        return symptomMapper.toSymptomResponse(savedSymptom);
    }

    @Override
    public SymptomResponse updateSymptom(Long id, SymptomRequest request, MultipartFile imageFile) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SYMPTOM_NOT_FOUND));

        if (!symptom.getName().equals(request.getName()) &&
                symptomRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SYMPTOM_NAME_EXISTS);
        }

        symptom.setName(request.getName());
        symptom.setDescription(request.getDescription());
        symptom.setBodyPart(BodyPart.fromName(request.getBodyPart()));

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String oldImageUrl = symptom.getImageUrl();
                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    awss3Service.deleteFile(oldImageUrl);
                }
                String newImageUrl = awss3Service.uploadFile(imageFile, S3Folder.SYMPTOMS.getFolderName());
                symptom.setImageUrl(newImageUrl);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        Symptom updatedSymptom = symptomRepository.save(symptom);
        return symptomMapper.toSymptomResponse(updatedSymptom);
    }

    @Override
    public void deleteSymptom(Long id) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SYMPTOM_NOT_FOUND));

        if (symptom.getImageUrl() != null && !symptom.getImageUrl().isEmpty()) {
            try {
                awss3Service.deleteFile(symptom.getImageUrl());
            } catch (Exception e) {
                throw new AppException(ErrorCode.DELETE_FILE_ERROR);
            }
        }

        symptomRepository.delete(symptom);
    }

    @Override
    public List<SymptomResponse> getSymptomByBodyPart(BodyPart bodyPart) {
        List<Symptom> symptoms = symptomRepository.findAllByBodyPart(bodyPart);
        return symptoms.stream().map(symptomMapper::toSymptomResponse).collect(Collectors.toList());
    }
}

