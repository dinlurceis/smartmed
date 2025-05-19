package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Symptom}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymptomRequest implements Serializable {
    String name;
    String description;
    String bodyPart;
}