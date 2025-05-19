package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.AddressRequest;
import com.dinlurceis.smartmed.dto.response.AddressResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.AddressMapper;
import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.User;
import com.dinlurceis.smartmed.repository.AddressRepository;
import com.dinlurceis.smartmed.repository.UserRepository;
import com.dinlurceis.smartmed.service.AddressService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final AddressMapper addressMapper;

    @Override
    public List<AddressResponse> getMyAddress() {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        List<Address> addresses = addressRepository.findByUserId(user.getId());

        return addresses.stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse addAddress(AddressRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Address address = addressMapper.toAddress(request);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return addressMapper.toAddressResponse(savedAddress);
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        }

        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setUser(user);

        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long addressId) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
        );

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        }

        addressRepository.deleteById(addressId);
    }
}
