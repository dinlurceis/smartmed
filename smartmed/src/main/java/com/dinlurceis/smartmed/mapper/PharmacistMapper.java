package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.PharmacistRequest;
import com.dinlurceis.smartmed.dto.response.PharmacistResponse;
import com.dinlurceis.smartmed.model.Pharmacist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PharmacistMapper {
    Pharmacist toPharmacist(PharmacistRequest request);
    PharmacistResponse toPharmacistResponse(Pharmacist pharmacist);
}
