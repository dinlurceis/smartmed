package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.Cart;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.User}
 */
@Value
@Builder
public class UserResponse implements Serializable {
    Long id;
    String email;
    String fullName;
    String phoneNumber;
    String imageUrl;
    Set<String> roles;
}