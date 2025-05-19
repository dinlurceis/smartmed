package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Cart}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class CartTotalResponse implements Serializable {
    Long id;
    Set<CartItemResponse> cartItems;
    Double totalPrice;
    Integer totalQuantity;
}