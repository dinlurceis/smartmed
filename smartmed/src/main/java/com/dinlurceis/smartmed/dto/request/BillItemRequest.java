package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.Medicine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.BillItem}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemRequest implements Serializable {
    Long medicineId;
    Integer quantity;
    Double importPrice;
}