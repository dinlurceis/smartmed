package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.ScheduleRequest;
import com.dinlurceis.smartmed.dto.response.ScheduleResponse;
import com.dinlurceis.smartmed.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toSchedule(ScheduleRequest request);
    ScheduleResponse toScheduleResponse(Schedule schedule);
}
