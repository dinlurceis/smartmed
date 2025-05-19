package com.dinlurceis.smartmed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime appointmentTime;      // thời gian cuộc hẹn

    String note;           // ghi chú tư vấn của bác sĩ

    @ManyToOne
    @JsonIgnore
    User patient;                       // người bệnh

    @ManyToOne
    @JsonIgnore
    User doctor;                        // bác sĩ tư vấn

    @ManyToMany
    @JoinTable(
            name = "AppointmentMedicines",
            joinColumns = @JoinColumn(name = "appointmentId"),
            inverseJoinColumns = @JoinColumn(name = "medicineId")
    )
    @JsonIgnore
    Set<Medicine> medicines;

    @ManyToMany
    @JoinTable(
            name = "AppointmentDiseases",
            joinColumns = @JoinColumn(name = "appointmentId"),
            inverseJoinColumns = @JoinColumn(name = "diseaseId")
    )
    @JsonIgnore
    Set<Disease> diseases;

    @OneToOne
    @JsonIgnore
    Schedule schedule;
}
