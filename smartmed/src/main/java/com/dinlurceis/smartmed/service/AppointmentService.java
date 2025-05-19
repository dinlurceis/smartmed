package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.AppointmentRequest;
import com.dinlurceis.smartmed.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getAppointments(int page, int size);

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse createAppointment(AppointmentRequest request);

    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);
}
