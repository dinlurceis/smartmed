package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.AuthenticationRequest;
import com.dinlurceis.smartmed.dto.request.IntrospectRequest;
import com.dinlurceis.smartmed.dto.request.UserCreateRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.AuthenticationResponse;
import com.dinlurceis.smartmed.dto.response.IntrospectResponse;
import com.dinlurceis.smartmed.dto.response.UserResponse;
import com.dinlurceis.smartmed.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserCreateRequest request){
        return ApiResponse.<UserResponse>builder()
                .message("Sign up successfully")
                .result(authenticationService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Authenticate completely")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        log.info("come here");
        return ApiResponse.<IntrospectResponse>builder()
                .message("Authenticate completely")
                .result(authenticationService.introspect(request))
                .build();
    }
}
