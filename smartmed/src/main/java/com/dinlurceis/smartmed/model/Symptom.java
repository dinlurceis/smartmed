package com.dinlurceis.smartmed.model;

import com.dinlurceis.smartmed.domain.BodyPart;
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
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String description;

    BodyPart bodyPart;

    String imageUrl;

    @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SymptomDisease> symptomDiseases;
}
