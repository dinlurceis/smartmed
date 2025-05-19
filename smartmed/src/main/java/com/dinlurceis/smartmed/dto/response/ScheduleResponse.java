package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Schedule}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleResponse implements Serializable {
    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime createdAt;
    ScheduleStatus status;
    User patient;
    User doctor;
}