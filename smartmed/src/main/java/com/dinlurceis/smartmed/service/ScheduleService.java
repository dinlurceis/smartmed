package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.dto.request.ScheduleRequest;
import com.dinlurceis.smartmed.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getSchedules(int page, int size);

    ScheduleResponse getScheduleById(Long scheduleId);

    ScheduleResponse createSchedule(ScheduleRequest request);

    ScheduleResponse updateScheduleStatus(Long scheduleId, ScheduleStatus status);

    ScheduleResponse updateSchedule(Long scheduleId, ScheduleRequest request);
}
