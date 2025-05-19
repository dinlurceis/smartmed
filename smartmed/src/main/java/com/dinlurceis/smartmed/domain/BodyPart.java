package com.dinlurceis.smartmed.domain;

import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;

public enum BodyPart {
    HEAD("Head"),
    FACE("Face"),
    THROAT("Throat"),
    CHEST("Chest"),
    STOMACH("Stomach"),
    BACK("Back"),
    ARMS("Arms"),
    LEGS("Legs"),
    JOINTS("Joints"),
    MUSCLES("Muscles"),
    SKIN("Skin"),
    DIGESTIVE_SYSTEM("Digestive System"),
    RESPIRATORY_SYSTEM("Respiratory System"),
    CARDIOVASCULAR_SYSTEM("Cardiovascular System"),
    NERVOUS_SYSTEM("Nervous System"),
    GENERAL_BODY("General Body");

    private final String name;

    BodyPart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BodyPart fromName(String name) {
        for (BodyPart bodyPart : values()) {
            if (bodyPart.getName().equals(name)) {
                return bodyPart;
            }
        }
        throw new AppException(ErrorCode.BODY_PART_NOT_FOUND);
    }
}

