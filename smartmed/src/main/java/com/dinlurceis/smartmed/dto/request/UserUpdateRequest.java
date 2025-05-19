package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.User}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest implements Serializable {
    String fullName;
    String phoneNumber;
}