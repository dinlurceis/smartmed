package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;


@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest implements Serializable {
    String password;
    String email;
}