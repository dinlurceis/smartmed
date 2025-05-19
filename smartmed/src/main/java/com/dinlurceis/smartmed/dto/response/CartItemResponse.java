package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Medicine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.CartItem}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CartItemResponse implements Serializable {
    Long id;
    Long medicineId;
    int quantity;
}