package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.model.Disease;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Appointment}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRequest implements Serializable {
    String note;
    List<Long> medicineIds;
    List<Long> diseaseIds;
    Long scheduleId;
}