package com.dinlurceis.smartmed.domain;

import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;

public enum MedicalSpecialty {
    GENERAL_EXAMINATION("General Examination"),
    INTERNAL_MEDICINE("Internal Medicine"),
    SURGERY("Surgery"),
    OBSTETRICS_GYNECOLOGY("Obstetrics & Gynecology"),
    PEDIATRICS("Pediatrics"),
    ENT("Ear, Nose & Throat"),
    OPHTHALMOLOGY("Ophthalmology"),
    DERMATOLOGY("Dermatology"),
    DENTAL("Dental"),
    CARDIOLOGY("Cardiology"),
    NEUROLOGY("Neurology"),
    ORTHOPEDICS("Orthopedics"),
    TRADITIONAL_MEDICINE("Traditional Medicine"),
    NUTRITION("Nutrition");

    private final String name;

    MedicalSpecialty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MedicalSpecialty fromName(String name) {
        for (MedicalSpecialty specialty : values()) {
            if (specialty.getName().equals(name)) {
                return specialty;
            }
        }
        throw new AppException(ErrorCode.SPECIALTY_NOT_FOUND);
    }

}
