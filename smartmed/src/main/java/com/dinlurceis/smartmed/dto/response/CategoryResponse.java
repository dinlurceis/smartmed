package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Category}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse implements Serializable {
    Long id;
    String name;
}