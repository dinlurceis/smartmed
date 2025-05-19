package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.BillRequest;
import com.dinlurceis.smartmed.dto.response.BillResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface BillService {
    List<BillResponse> getBills(int page, int size);

    BillResponse getBillById(Long id);

    BillResponse createBill(@Valid BillRequest request);
}
