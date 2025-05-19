package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Order}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class OrderRequest implements Serializable {
    Long addressId;
    @NotNull
    String fullName;
    @NotNull
    String phoneNumber;
    String note;
    List<Long> itemIds;
}