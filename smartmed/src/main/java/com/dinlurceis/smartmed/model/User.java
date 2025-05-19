package com.dinlurceis.smartmed.model;

import com.dinlurceis.smartmed.domain.AccountStatus;
import com.dinlurceis.smartmed.domain.MedicalSpecialty;
import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = true)
    String password;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String fullName;

    String phoneNumber;

    boolean active;

    String imageUrl;

    Set<String> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<Review> reviews;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<Schedule> schedulesOfPatient;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<Schedule> schedulesOfDoctor;

    @Column(nullable = true)
    MedicalSpecialty medicalSpecialty;

}