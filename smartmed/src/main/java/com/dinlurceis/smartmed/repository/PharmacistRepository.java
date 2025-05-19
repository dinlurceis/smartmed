package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Pharmacist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Page<Pharmacist> findAll(Specification<Pharmacist> spec, Pageable pageable);
}
