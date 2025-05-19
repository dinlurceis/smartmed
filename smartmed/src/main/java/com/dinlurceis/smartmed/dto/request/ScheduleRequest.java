package com.dinlurceis.smartmed.dto.request;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.dinlurceis.smartmed.model.Schedule}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ScheduleRequest implements Serializable {
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long doctorId;
}