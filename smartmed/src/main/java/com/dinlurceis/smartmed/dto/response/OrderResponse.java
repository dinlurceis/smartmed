package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.OrderItem;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Order}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OrderResponse implements Serializable {
    Long id;
    @JsonBackReference
    User user;
    @JsonBackReference
    Address address;
    LocalDateTime orderTime;
    @NotNull
    String fullName;
    @NotNull
    String phoneNumber;
    String note;
    @JsonBackReference
    Set<OrderItem> orderItems;
    OrderStatus orderStatus;
    Integer totalQuantity;
    Double totalPrice;
}