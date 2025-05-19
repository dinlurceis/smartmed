package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.BillItemRequest;
import com.dinlurceis.smartmed.dto.response.BillItemResponse;
import com.dinlurceis.smartmed.model.BillItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillItemMapper {
    BillItem toBillItem(BillItemRequest request);
    BillItemResponse toBillItemResponse(BillItem billItem);
}
