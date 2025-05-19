package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    boolean existsByName(String name);
}
