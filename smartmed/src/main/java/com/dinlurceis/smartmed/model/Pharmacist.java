package com.dinlurceis.smartmed.model;

import com.dinlurceis.smartmed.domain.AccountStatus;
import com.dinlurceis.smartmed.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pharmacist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;              // tên nhà thuốc / đơn vị cung cấp

    String phone;

    String email;

    String licenseNumber;

    @OneToMany
    @JsonManagedReference
    Set<Bill> bills;
}
