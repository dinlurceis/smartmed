package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.BillItem;
import com.dinlurceis.smartmed.model.Pharmacist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Bill}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponse implements Serializable {
    Long id;
    PharmacistResponse pharmacist;
    List<BillItemResponse> billItems;
    LocalDateTime createdAt;
    Integer totalQuantity;
    Double totalPrice;

}