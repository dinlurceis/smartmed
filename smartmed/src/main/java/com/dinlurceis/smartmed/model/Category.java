package com.dinlurceis.smartmed.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonManagedReference
    Set<Category> childCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    Set<Medicine> medicines;

    @JsonIgnore
    public Set<Medicine> getAllMedicines() {
        Set<Medicine> allMedicines = new HashSet<>(medicines);
        if (childCategories != null) {
            childCategories.forEach(child -> {
                allMedicines.addAll(child.getAllMedicines());
            });
        }

        return allMedicines;
    }
}