package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Disease;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.Schedule;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Appointment}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponse implements Serializable {
    Long id;
    LocalDateTime appointmentTime;
    String note;
    User doctor;
    Set<Medicine> medicines;
    Set<Disease> diseases;
    Schedule schedule;
}