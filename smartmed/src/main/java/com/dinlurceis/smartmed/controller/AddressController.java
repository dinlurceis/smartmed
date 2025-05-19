package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.AddressRequest;
import com.dinlurceis.smartmed.dto.response.AddressResponse;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    ApiResponse<List<AddressResponse>> getMyAddress(){
        return ApiResponse.<List<AddressResponse>>builder()
                .message("Get my address successfully")
                .result(addressService.getMyAddress())
                .build();
    }
    
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    ApiResponse<AddressResponse> addAddress(@RequestBody AddressRequest request){
        return ApiResponse.<AddressResponse>builder()
                .message("Add address successfully")
                .result(addressService.addAddress(request))
                .build();
    }
    
    @PatchMapping("/update/{addressId}")
    @PreAuthorize("isAuthenticated()")
    ApiResponse<AddressResponse> updateAddress(@PathVariable Long addressId,
                                               @RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .message("Update address successfully")
                .result(addressService.updateAddress(addressId, request))
                .build();
    }

    @DeleteMapping("/delete/{addressId}")
    @PreAuthorize("isAuthenticated()")
    ApiResponse<?> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ApiResponse.<Void>builder()
                .message("Delete address successfully")
                .build();
    }
}
