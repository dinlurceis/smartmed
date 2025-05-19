package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Symptom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.SymptomDisease}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymptomDiseaseResponse implements Serializable {
    Long id;
    Symptom symptom;
    Boolean isKeySymptom;
}