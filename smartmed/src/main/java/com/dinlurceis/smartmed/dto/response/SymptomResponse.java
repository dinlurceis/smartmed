package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Symptom}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymptomResponse implements Serializable {
    Long id;
    String name;
    String description;
    BodyPart bodyPart;
    String imageUrl;
}