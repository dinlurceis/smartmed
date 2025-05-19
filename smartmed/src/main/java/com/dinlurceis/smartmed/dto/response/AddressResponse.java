package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Pharmacist;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Address}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse implements Serializable {
    Long id;
    String address;
    String city;
    String state;
}