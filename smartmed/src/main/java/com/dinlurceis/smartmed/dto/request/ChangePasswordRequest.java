package com.dinlurceis.smartmed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ChangePasswordRequest {
    String email;
    String password;
    String newPassword;
    String confirmPassword;
}
