package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.domain.MedicalSpecialty;
import com.dinlurceis.smartmed.model.Address;
import com.dinlurceis.smartmed.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAllByMedicalSpecialtyIsNotNull(Pageable pageable);

    Page<User> findAllByMedicalSpecialtyIsNotNullAndMedicalSpecialty(MedicalSpecialty medicalSpecialty, Pageable pageable);
}
