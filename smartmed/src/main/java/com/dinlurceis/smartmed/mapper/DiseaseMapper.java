package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.DiseaseRequest;
import com.dinlurceis.smartmed.dto.response.DiseaseResponse;
import com.dinlurceis.smartmed.model.Disease;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiseaseMapper {
    DiseaseResponse toDiseaseResponse(Disease disease);
    Disease toDisease (DiseaseRequest request);
}
