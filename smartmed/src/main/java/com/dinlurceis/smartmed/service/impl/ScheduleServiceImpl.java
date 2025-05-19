package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.dinlurceis.smartmed.model.Schedule;
import com.dinlurceis.smartmed.model.User;
import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.repository.ScheduleRepository;
import com.dinlurceis.smartmed.repository.UserRepository;
import com.dinlurceis.smartmed.dto.request.ScheduleRequest;
import com.dinlurceis.smartmed.dto.response.ScheduleResponse;
import com.dinlurceis.smartmed.mapper.ScheduleMapper;
import com.dinlurceis.smartmed.util.SecurityUtils;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleResponse> getSchedules(int page, int size) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Schedule> schedulePage;
        if (user.getRoles().contains("DOCTOR")) {
            schedulePage = scheduleRepository.findByDoctorId(user.getId(), pageable);
        }
        else {
            schedulePage = scheduleRepository.findByPatientId(user.getId(), pageable);
        }
        return schedulePage.stream().map(scheduleMapper::toScheduleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResponse getScheduleById(Long id) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        
        User user = userRepository.findByEmail(email);
        // kiểm tra xem schedule có thuộc về người đang đăng nhập k, k ktra admin
        if (user.getRoles().size() == 1 && user.getRoles().contains("PATIENT")) {
            if (!schedule.getPatient().getId().equals(user.getId())) {
                throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
            }
        }
        else if (user.getRoles().contains("DOCTOR")) {
            if (!schedule.getDoctor().getId().equals(user.getId())) {
                throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
            }
        }
        
        return scheduleMapper.toScheduleResponse(schedule);
    }

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        String email = SecurityUtils.getCurrentLogin()
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User patient = userRepository.findByEmail(email);
        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow(
                () -> new AppException(ErrorCode.DOCTOR_NOT_FOUND)
        );
        if (!patient.getRoles().contains("PATIENT") || !doctor.getRoles().contains("DOCTOR")) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        // Validate time
        validateScheduleTime(request.getStartTime(), request.getEndTime());
        
        // Check for time conflicts
        if (hasTimeConflict(doctor.getId(), request.getStartTime(), request.getEndTime())) {
            throw new AppException(ErrorCode.SCHEDULE_TIME_CONFLICT);
        }

        Schedule schedule = Schedule.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createdAt(LocalDateTime.now())
                .status(ScheduleStatus.PENDING)
                .doctor(doctor)
                .patient(patient)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleResponse(savedSchedule);
    }

    @Override
    public ScheduleResponse updateScheduleStatus(Long id, ScheduleStatus status) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getStatus() == ScheduleStatus.COMPLETED || schedule.getStatus() == ScheduleStatus.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_STATUS_TRANSITION);
        }
        
        schedule.setStatus(status);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleResponse(updatedSchedule);
    }

    @Override
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        // check xem nếu là pending
        if (schedule.getStatus() != ScheduleStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_CANNOT_UPDATE);
        }

        // check điều kiện thời gian
        if (!schedule.getStartTime().equals(request.getStartTime()) || 
            !schedule.getEndTime().equals(request.getEndTime())) {
            
            validateScheduleTime(request.getStartTime(), request.getEndTime());
            
            // Check conflicts excluding current schedule
            if (hasTimeConflict(schedule.getDoctor().getId(), 
                    request.getStartTime(), request.getEndTime(), id)) {
                throw new AppException(ErrorCode.SCHEDULE_TIME_CONFLICT);
            }
        }

        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleResponse(updatedSchedule);
    }

    private void validateScheduleTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new AppException(ErrorCode.INVALID_SCHEDULE_TIME);
        }

        if (java.time.Duration.between(startTime, endTime).toMinutes() > 60) {
            throw new AppException(ErrorCode.SCHEDULE_TIME_MUST_BE_LESS_THAN_ONE_HOUR);
        }

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.SCHEDULE_TIME_PAST);
        }
    }

    private boolean hasTimeConflict(Long doctorId, LocalDateTime startTime,
            LocalDateTime endTime) {
        return hasTimeConflict(doctorId, startTime, endTime, null);
    }

    private boolean hasTimeConflict(Long doctorId, LocalDateTime startTime, 
            LocalDateTime endTime, Long excludeScheduleId) {
        List<Schedule> doctorSchedules;
        if (excludeScheduleId != null) {
            doctorSchedules = scheduleRepository.findByDoctorIdAndIdNot(doctorId, excludeScheduleId);
        } else {
            doctorSchedules = scheduleRepository.findByDoctorId(doctorId);
        }

        return doctorSchedules.stream()
                .anyMatch(s -> 
                    (startTime.isBefore(s.getEndTime()) && endTime.isAfter(s.getStartTime())) &&
                    s.getStatus() != ScheduleStatus.CANCELLED
                );
    }
}