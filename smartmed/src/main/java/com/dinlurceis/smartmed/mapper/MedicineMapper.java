package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.MedicineCreateRequest;
import com.dinlurceis.smartmed.dto.response.MedicineResponse;
import com.dinlurceis.smartmed.model.Medicine;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
    MedicineResponse toMedicineResponse(Medicine medicine);

    Medicine toMedicine(MedicineCreateRequest request);
}
