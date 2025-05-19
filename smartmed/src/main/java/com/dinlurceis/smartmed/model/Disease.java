package com.dinlurceis.smartmed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
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
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String imageUrl;

    String overview; // Tổng quan

    String causes; // Nguyên nhân

    String complications; // Biến chứng

    String transmission; // Lây lan

    String riskFactors; // Đối tượng nguy cơ

    String prevention; // Phòng ngừa

    String diagnosis; // Chẩn đoán

    String treatment; // Điều trị

    String genderRestriction; // Giới tính: "M", "F", "ALL"

    Integer ageMin; // Độ tuổi tối thiểu

    Integer ageMax; // Độ tuổi tối đa

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<SymptomDisease> symptomDiseases;

    @ManyToMany
    @JsonIgnore
    Set<Appointment> appointments;
}
