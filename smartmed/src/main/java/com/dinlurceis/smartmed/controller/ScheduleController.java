package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.dinlurceis.smartmed.dto.request.ScheduleRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.ScheduleResponse;
import com.dinlurceis.smartmed.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<PageResponse<List<ScheduleResponse>>> getSchedules(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return ApiResponse.<PageResponse<List<ScheduleResponse>>>builder()
                .message("Get schedules successfully")
                .result(PageResponse.<List<ScheduleResponse>>builder()
                        .currentPage(page)
                        .items(scheduleService.getSchedules(page, size))
                        .pageSize(size)
                        .totalPages(10)
                        .totalElements(100L)
                        .build())
                .build();
    }

    @GetMapping("/{scheduleId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<ScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ApiResponse.<ScheduleResponse>builder()
                .message("Get schedule successfully")
                .result(scheduleService.getScheduleById(scheduleId))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        return ApiResponse.<ScheduleResponse>builder()
                .message("Create schedule successfully")
                .result(scheduleService.createSchedule(request))
                .build();
    }

    @PatchMapping("/update-status/{scheduleId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ApiResponse<ScheduleResponse> updateScheduleStatus(
            @PathVariable Long scheduleId,
            @RequestParam String status) {
        return ApiResponse.<ScheduleResponse>builder()
                .message("Update schedule status successfully")
                .result(scheduleService.updateScheduleStatus(scheduleId, ScheduleStatus.valueOf(status.toUpperCase())))
                .build();
    }

    @PutMapping("/update/{scheduleId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest request) {
        return ApiResponse.<ScheduleResponse>builder()
                .message("Update schedule successfully")
                .result(scheduleService.updateSchedule(scheduleId, request))
                .build();
    }
}