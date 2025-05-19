package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.response.AppointmentResponse;
import com.dinlurceis.smartmed.model.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentResponse toAppointmentResponse(Appointment appointment);
}
