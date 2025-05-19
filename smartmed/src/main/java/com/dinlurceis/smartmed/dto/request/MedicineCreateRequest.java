package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Medicine}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineCreateRequest implements Serializable {
    String name;
    String manufacturer;
    String packaging;
    Double price;
    Integer quantity;
    String productDetails;
    String sideEffects;
    String instructions;
    String ingredients;
    Long categoryId;
}