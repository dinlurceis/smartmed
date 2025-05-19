package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.AddressRequest;
import com.dinlurceis.smartmed.dto.response.AddressResponse;
import com.dinlurceis.smartmed.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressRequest request);
    AddressResponse toAddressResponse(Address address);
}
