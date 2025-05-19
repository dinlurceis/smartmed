package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.SymptomDisease;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Disease}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiseaseResponse implements Serializable {
    Long id;
    String name;
    String imageUrl;
    String overview;
    String causes;
    String complications;
    String transmission;
    String riskFactors;
    String prevention;
    String diagnosis;
    String treatment;
    String genderRestriction;
    Integer ageMin;
    Integer ageMax;
    Set<SymptomDisease> symptomDiseases;
}