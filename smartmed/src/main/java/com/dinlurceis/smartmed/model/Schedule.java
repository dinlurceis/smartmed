package com.dinlurceis.smartmed.model;

import com.dinlurceis.smartmed.domain.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime startTime;
    LocalDateTime endTime;

    ScheduleStatus status; // AVAILABLE, BOOKED, CANCELLED,...

    LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    User patient;

    @ManyToOne
    @JsonIgnore
    User doctor;

    @OneToOne
    @JsonIgnore
    Appointment appointment;
}