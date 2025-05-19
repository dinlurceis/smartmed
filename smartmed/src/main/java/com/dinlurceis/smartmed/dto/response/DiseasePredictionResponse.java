package com.dinlurceis.smartmed.dto.response;

import com.dinlurceis.smartmed.model.Disease;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class DiseasePredictionResponse {
    private Disease disease;
    private Double score;
    private Map<String, Double> scoreDetails;

}
