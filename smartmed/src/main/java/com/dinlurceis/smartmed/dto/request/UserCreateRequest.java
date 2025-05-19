package com.dinlurceis.smartmed.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    String email;
    String fullName;
    String password;
}
