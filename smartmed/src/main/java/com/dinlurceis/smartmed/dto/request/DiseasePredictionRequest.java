package com.dinlurceis.smartmed.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DiseasePredictionRequest {
    String gender;
    int age;
    List<Long> symptomIds;
}
