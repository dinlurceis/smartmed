package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.DiseasePredictionRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.DiseasePredictionResponse;
import com.dinlurceis.smartmed.service.SymptomDiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/disease-predict")
public class SymptomDiseaseController {
    private final SymptomDiseaseService symptomDiseaseService;

    @GetMapping
    public ApiResponse<List<DiseasePredictionResponse>> predictDisease(@RequestBody DiseasePredictionRequest request) {
        return ApiResponse.<List<DiseasePredictionResponse>>builder()
                .message("Predict disease successfully")
                .result(symptomDiseaseService.predictDisease(request))
                .build();
    }
}
