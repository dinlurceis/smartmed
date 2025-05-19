package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.DiseasePredictionRequest;
import com.dinlurceis.smartmed.dto.response.DiseasePredictionResponse;

import java.util.List;

public interface SymptomDiseaseService {
    List<DiseasePredictionResponse> predictDisease(DiseasePredictionRequest request);
}
