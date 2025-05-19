package com.dinlurceis.smartmed.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.BillItem}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemResponse implements Serializable {
    Long id;
    Long medicineId;
    Integer quantity;
    Double importPrice;
}