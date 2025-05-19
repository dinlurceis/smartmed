package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findByDoctorId(Long doctorId, Pageable pageable);

    Page<Schedule> findByPatientId(Long patientId, Pageable pageable);

    List<Schedule> findByDoctorIdAndIdNot(Long doctorId, Long excludeScheduleId);

    List<Schedule> findByDoctorId(Long doctorId);
}
