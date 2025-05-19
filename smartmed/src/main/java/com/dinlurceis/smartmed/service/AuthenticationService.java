package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.AuthenticationRequest;
import com.dinlurceis.smartmed.dto.request.IntrospectRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.response.AuthenticationResponse;
import com.dinlurceis.smartmed.dto.response.IntrospectResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    UserResponse register(UserCreateRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;
}
