package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.AppointmentRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.AppointmentResponse;
import com.dinlurceis.smartmed.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ApiResponse<List<AppointmentResponse>> getPatientAppointments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .message("Get patient appointments successfully")
                .result(appointmentService.getAppointments(page, size))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ApiResponse<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        return ApiResponse.<AppointmentResponse>builder()
                .message("Get appointment successfully")
                .result(appointmentService.getAppointmentById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .message("Create appointment successfully")
                .result(appointmentService.createAppointment(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ApiResponse<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .message("Update appointment successfully")
                .result(appointmentService.updateAppointment(id, request))
                .build();
    }

}