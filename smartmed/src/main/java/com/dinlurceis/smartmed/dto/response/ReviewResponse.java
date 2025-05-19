package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Review}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewResponse implements Serializable {
    Long id;
    String reviewText;
    double rating;
    LocalDateTime createdAt;
    Medicine medicine;
    User user;
}