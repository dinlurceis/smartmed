package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Pharmacist}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PharmacistRequest implements Serializable {
    String name;
    String phone;
    String email;
    String licenseNumber;
}