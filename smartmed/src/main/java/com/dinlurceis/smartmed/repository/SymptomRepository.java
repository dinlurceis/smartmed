package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.dinlurceis.smartmed.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    boolean existsByName(String name);

    List<Symptom> findAllByBodyPart(BodyPart bodyPart);
}
