package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.SymptomDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomDiseaseRepository extends JpaRepository<SymptomDisease, Long> {
    List<SymptomDisease> findAllBySymptomIdIn(List<Long> symptomIds);

    CharSequence findAllBySymptom_id(Long id);
}
