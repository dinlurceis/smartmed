package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.dto.request.AppointmentRequest;
import com.dinlurceis.smartmed.dto.response.AppointmentResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.AppointmentMapper;
import com.dinlurceis.smartmed.model.*;
import com.dinlurceis.smartmed.repository.*;
import com.dinlurceis.smartmed.service.AppointmentService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final DiseaseRepository diseaseRepository;
    private final MedicineRepository medicineRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentResponse> getAppointments(int page, int size) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "appointmentTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Appointment> appointments;
        if (user.getRoles().size() == 1 && user.getRoles().contains("PATIENT")) {
            appointments = appointmentRepository.findAllByPatient_Id(user.getId(), pageable);
        }
        else if (user.getRoles().contains("DOCTOR")) {
            appointments = appointmentRepository.findAllByDoctor_Id(user.getId(), pageable);
        }
        else {
            appointments = appointmentRepository.findAll(pageable);
        }
        return appointments.getContent()
                .stream()
                .map(appointmentMapper::toAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        
        // appointment của patient hoặc doctor thì mới được xem, hoặc admin mới được xem
        if ((!appointment.getDoctor().getId().equals(user.getId()) &&
            !appointment.getPatient().getId().equals(user.getId()) || user.getRoles().contains("ADMIN"))) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getDoctor().getEmail().equals(email)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        if (schedule.getStatus() != ScheduleStatus.CONFIRMED) {
            throw new AppException(ErrorCode.STATUS_IS_NOT_CONFIRMED);
        }
        schedule.setStatus(ScheduleStatus.COMPLETED);
        scheduleRepository.save(schedule);

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(schedule.getStartTime());
        appointment.setNote(request.getNote());
        appointment.setDoctor(schedule.getDoctor());
        appointment.setPatient(schedule.getPatient());
        appointment.setSchedule(schedule);

        Set<Disease> diseases = request.getDiseaseIds().stream()
                .map(id -> diseaseRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.DISEASE_NOT_FOUND)))
                .collect(Collectors.toSet());
        appointment.setDiseases(diseases);

        Set<Medicine> medicines = request.getMedicineIds().stream()
                .map(id -> medicineRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND)))
                .collect(Collectors.toSet());
        appointment.setMedicines(medicines);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if (!appointment.getDoctor().getEmail().equals(email)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        if (appointment.getSchedule().getStatus() != ScheduleStatus.COMPLETED) {
            throw new AppException(ErrorCode.STATUS_IS_NOT_COMPLETED);
        }

        appointment.setNote(request.getNote());

        appointment.getDiseases().clear();
        Set<Disease> diseases = request.getDiseaseIds().stream()
                .map(diseaseId -> diseaseRepository.findById(diseaseId)
                        .orElseThrow(() -> new AppException(ErrorCode.DISEASE_NOT_FOUND)))
                .collect(Collectors.toSet());
        appointment.setDiseases(diseases);

        appointment.getMedicines().clear();
        Set<Medicine> medicines = request.getMedicineIds().stream()
                .map(medicineId -> medicineRepository.findById(medicineId)
                        .orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND)))
                .collect(Collectors.toSet());
        appointment.setMedicines(medicines);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(updatedAppointment);
    }
}