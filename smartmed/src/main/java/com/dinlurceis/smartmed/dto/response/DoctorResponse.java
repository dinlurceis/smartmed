package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.domain.MedicalSpecialty;
import com.dinlurceis.smartmed.model.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.User}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorResponse implements Serializable {
    Long id;
    String email;
    String fullName;
    boolean active;
    String imageUrl;
    Set<Schedule> schedulesOfDoctor;
    String medicalSpecialty;
}