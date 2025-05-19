package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByPatient_Id(Long id, Pageable pageable);

    Page<Appointment> findAllByDoctor_Id(Long id, Pageable pageable);
}
