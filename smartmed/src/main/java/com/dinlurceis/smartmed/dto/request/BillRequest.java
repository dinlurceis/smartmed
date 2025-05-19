package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.BillItem;
import com.dinlurceis.smartmed.model.Pharmacist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Bill}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequest implements Serializable {
    Long pharmacistId;
    List<BillItemRequest> billItems;
}