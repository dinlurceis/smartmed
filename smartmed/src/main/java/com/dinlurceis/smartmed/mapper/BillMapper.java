package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.BillRequest;
import com.dinlurceis.smartmed.dto.response.BillResponse;
import com.dinlurceis.smartmed.model.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillResponse toBillResponse(Bill bill);
    Bill toBill(BillRequest request);
}
