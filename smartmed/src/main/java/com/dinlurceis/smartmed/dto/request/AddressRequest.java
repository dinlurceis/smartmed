package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Address}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest implements Serializable {
    String address;
    String city;
    String state;
}