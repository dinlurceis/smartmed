package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Category;
import com.dinlurceis.smartmed.model.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Medicine}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineResponse implements Serializable {
    Long id;
    String name;
    String imageUrl;
    String manufacturer;
    String packaging;
    Double price;
    Integer quantity;
    String productDetails;
    String sideEffects;
    String instructions;
    String ingredients;
    @NotNull
    @JsonBackReference
    Category category;
    @JsonBackReference
    Set<Review> reviews;
}