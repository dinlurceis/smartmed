package com.dinlurceis.smartmed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SymptomDisease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "symptomId", nullable = false)
    @JsonIgnore
    Symptom symptom;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "diseaseId", nullable = false)
    Disease disease;

    @Column
    Boolean isKeySymptom; // Triệu chứng đặc trưng
}
