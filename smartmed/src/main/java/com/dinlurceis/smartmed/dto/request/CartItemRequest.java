package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.CartItem}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemRequest implements Serializable {
    @NotNull
    Long medicineId;
    @NotNull
    int quantity;
}