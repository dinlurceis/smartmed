package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.S3Folder;
import com.dinlurceis.smartmed.dto.request.DiseaseRequest;
import com.dinlurceis.smartmed.dto.request.SymptomDiseaseRequest;
import com.dinlurceis.smartmed.dto.response.DiseasePredictionResponse;
import com.dinlurceis.smartmed.dto.response.DiseaseResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.DiseaseMapper;
import com.dinlurceis.smartmed.model.Disease;
import com.dinlurceis.smartmed.model.Symptom;
import com.dinlurceis.smartmed.model.SymptomDisease;
import com.dinlurceis.smartmed.repository.DiseaseRepository;
import com.dinlurceis.smartmed.repository.SymptomDiseaseRepository;
import com.dinlurceis.smartmed.repository.SymptomRepository;
import com.dinlurceis.smartmed.service.AWSS3Service;
import com.dinlurceis.smartmed.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final DiseaseMapper diseaseMapper;
    private final AWSS3Service awss3Service;
    private final SymptomDiseaseRepository symptomDiseaseRepository;

    @Override
    public List<DiseaseResponse> getDiseases(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return diseaseRepository.findAll(pageRequest)
                .stream()
                .map(diseaseMapper::toDiseaseResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DiseaseResponse getDiseaseById(Long id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DISEASE_NOT_FOUND));
        return diseaseMapper.toDiseaseResponse(disease);
    }

    @Override
    public DiseaseResponse createDisease(DiseaseRequest request, MultipartFile imageFile) {
        if (diseaseRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.DISEASE_NAME_EXISTS);
        }

        Disease disease = diseaseMapper.toDisease(request);

        if (imageFile != null) {
            try {
                String imageUrl = awss3Service.uploadFile(imageFile, S3Folder.DISEASE.getFolderName());
                disease.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        Set<SymptomDisease> symptomDiseases = new HashSet<>();
        for (SymptomDiseaseRequest currentRequest : request.getSymptomDiseaseRequests()) {
            Symptom symptom = symptomRepository.findById(currentRequest.getSymptomId())
                    .orElseThrow(() -> new AppException(ErrorCode.SYMPTOM_NOT_FOUND));

            SymptomDisease symptomDisease = new SymptomDisease();
            symptomDisease.setSymptom(symptom);
            symptomDisease.setDisease(disease);
            symptomDisease.setIsKeySymptom(currentRequest.getIsKeySymptom());
            symptomDiseases.add(symptomDisease);
        }
        disease.setSymptomDiseases(symptomDiseases);

        Disease savedDisease = diseaseRepository.save(disease);

        return diseaseMapper.toDiseaseResponse(savedDisease);
    }

    @Override
    public DiseaseResponse updateDisease(Long id, DiseaseRequest request, MultipartFile imageFile) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DISEASE_NOT_FOUND));

        if (!disease.getName().equals(request.getName()) &&
                diseaseRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.DISEASE_NAME_EXISTS);
        }

        disease.setName(request.getName());
        disease.setOverview(request.getOverview());
        disease.setCauses(request.getCauses());
        disease.setComplications(request.getComplications());
        disease.setTransmission(request.getTransmission());
        disease.setRiskFactors(request.getRiskFactors());
        disease.setPrevention(request.getPrevention());
        disease.setDiagnosis(request.getDiagnosis());
        disease.setTreatment(request.getTreatment());
        disease.setGenderRestriction(request.getGenderRestriction());
        disease.setAgeMin(request.getAgeMin());
        disease.setAgeMax(request.getAgeMax());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                log.info("come here");
                String oldImageUrl = disease.getImageUrl();

                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    awss3Service.deleteFile(oldImageUrl);
                }
                String newImageUrl = awss3Service.uploadFile(imageFile, S3Folder.DISEASE.getFolderName());
                disease.setImageUrl(newImageUrl);

            } catch (Exception e) {
                throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        disease.getSymptomDiseases().clear();
        for (SymptomDiseaseRequest currentRequest : request.getSymptomDiseaseRequests()) {
            Symptom symptom = symptomRepository.findById(currentRequest.getSymptomId())
                    .orElseThrow(() -> new AppException(ErrorCode.SYMPTOM_NOT_FOUND));

            SymptomDisease symptomDisease = new SymptomDisease();
            symptomDisease.setSymptom(symptom);
            symptomDisease.setDisease(disease);
            symptomDisease.setIsKeySymptom(currentRequest.getIsKeySymptom());
            disease.getSymptomDiseases().add(symptomDisease);
        }

        Disease updatedDisease = diseaseRepository.save(disease);
        return diseaseMapper.toDiseaseResponse(updatedDisease);
    }

    @Override
    public void deleteDisease(Long id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DISEASE_NOT_FOUND));

        // ktra xem disease có đang ở trong appointment nào k
        if (!disease.getAppointments().isEmpty()) {
            throw new AppException(ErrorCode.DISEASE_IN_USE);
        }

        // xóa ảnh khỏi S3 nếu có
        if (disease.getImageUrl() != null && !disease.getImageUrl().isEmpty()) {
            try {
                awss3Service.deleteFile(disease.getImageUrl());
            } catch (Exception e) {
                throw new AppException(ErrorCode.DELETE_FILE_ERROR);
            }
        }

        diseaseRepository.delete(disease);
    }
}