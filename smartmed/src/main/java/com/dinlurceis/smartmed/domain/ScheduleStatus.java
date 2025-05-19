package com.dinlurceis.smartmed.domain;

public enum ScheduleStatus {
    PENDING("Pending"),                    // Chờ xác nhận từ bác sĩ
    CONFIRMED("Confirmed"),                // Đã được xác nhận
    REJECTED("Rejected"),                  // Bác sĩ từ chối lịch hẹn
    COMPLETED("Completed"),                // Đã hoàn thành khám
    NO_SHOW("No Show"),
    CANCELLED("Cancelled");                   // Bệnh nhân không đến

    private final String name;

    ScheduleStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

