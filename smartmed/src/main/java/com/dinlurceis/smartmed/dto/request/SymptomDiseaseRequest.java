package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.SymptomDisease}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymptomDiseaseRequest implements Serializable {
    Long symptomId;
    Boolean isKeySymptom;
}