package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.SymptomRequest;
import com.dinlurceis.smartmed.dto.response.SymptomResponse;
import com.dinlurceis.smartmed.model.Symptom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomMapper {
    Symptom toSymptom(SymptomRequest request);
    SymptomResponse toSymptomResponse(Symptom symptom);
}
