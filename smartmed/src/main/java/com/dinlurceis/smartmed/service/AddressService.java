package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.AddressRequest;
import com.dinlurceis.smartmed.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> getMyAddress();

    AddressResponse addAddress(AddressRequest request);

    AddressResponse updateAddress(Long addressId, AddressRequest request);

    void deleteAddress(Long addressId);
}
