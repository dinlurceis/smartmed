package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.PageRequestDTO;
import com.dinlurceis.smartmed.dto.request.PharmacistRequest;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.PharmacistResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface PharmacistService {
    PageResponse<List<PharmacistResponse>> getPharmacists(int page, int size);

    PharmacistResponse getPharmacistById(Long id);

    PharmacistResponse createPharmacist(@Valid PharmacistRequest request);

    PharmacistResponse updatePharmacist(Long id, @Valid PharmacistRequest request);

    void deletePharmacist(Long id);
}
