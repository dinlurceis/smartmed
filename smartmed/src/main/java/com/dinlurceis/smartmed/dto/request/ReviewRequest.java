package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.Medicine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Review}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRequest implements Serializable {
    String reviewText;
    double rating;
    Long medicineId;
}