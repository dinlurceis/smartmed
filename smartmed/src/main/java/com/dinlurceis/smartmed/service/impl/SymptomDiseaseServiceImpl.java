package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.DiseasePredictionRequest;
import com.dinlurceis.smartmed.dto.response.DiseasePredictionResponse;
import com.dinlurceis.smartmed.model.Disease;
import com.dinlurceis.smartmed.model.SymptomDisease;
import com.dinlurceis.smartmed.repository.SymptomDiseaseRepository;
import com.dinlurceis.smartmed.service.SymptomDiseaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SymptomDiseaseServiceImpl implements SymptomDiseaseService {

    private final SymptomDiseaseRepository symptomDiseaseRepository;

    @Override
    public List<DiseasePredictionResponse> predictDisease(DiseasePredictionRequest request) {
        List<Long> symptomIds = request.getSymptomIds();
        String gender = request.getGender();
        int age = request.getAge();

        List<SymptomDisease> matchedSymptomDiseases = symptomDiseaseRepository
                .findAllBySymptomIdIn(symptomIds);

        Map<Disease, List<SymptomDisease>> diseaseMap = matchedSymptomDiseases.stream()
                .collect(Collectors.groupingBy(SymptomDisease::getDisease));

        List<DiseasePredictionResponse> predictions = new ArrayList<>();

        for (Disease disease : diseaseMap.keySet()) {
            List<SymptomDisease> matchedSymptoms = diseaseMap.get(disease);

            // tính score thành phần
            Map<String, Double> scoreDetails = calculateScoreComponents(
                    disease,
                    matchedSymptoms,
                    symptomIds,
                    gender,
                    age
            );

            double finalScore = calculateFinalScore(scoreDetails);

            if (finalScore > 0) {
                predictions.add(new DiseasePredictionResponse(disease, finalScore, scoreDetails));
            }
        }

        // sắp xếp giảm dần
        predictions.sort((a, b) ->
                Double.compare(b.getScore(), a.getScore()));

        return predictions.subList(0, Math.min(predictions.size(), 2));
    }

    private Map<String, Double> calculateScoreComponents(
            Disease disease,
            List<SymptomDisease> matchedSymptoms,
            List<Long> inputSymptomIds,
            String gender,
            int age) {

        Map<String, Double> scores = new HashMap<>();

        // điểm matching (tổng số match chia cho tổng số triệu chứng của bệnh)
        int totalDiseaseSymptoms = disease.getSymptomDiseases().size();
        double matchingRatio = (double) matchedSymptoms.size() / totalDiseaseSymptoms;
        scores.put("matchingRatio", matchingRatio);

        // điểm key symptom (vd: bệnh sốt xuất huyết -> key là sốt)
        long keySymptomCount = matchedSymptoms.stream()
                .filter(SymptomDisease::getIsKeySymptom)
                .count();
        long totalKeySymptoms = disease.getSymptomDiseases().stream()
                .filter(SymptomDisease::getIsKeySymptom)
                .count();
        double keySymptomScore = totalKeySymptoms > 0 ?
                (double) keySymptomCount / totalKeySymptoms : 0.0;
        if (keySymptomScore > 1.0) keySymptomScore = 1.0;
        if (totalKeySymptoms > 0)
            scores.put("keySymptomScore", keySymptomScore); // bệnh nào k có key symptom thì k put điểm vào

        // điểm gender (có bệnh chỉ có nữ/ nam bị)
        double genderScore = calculateGenderScore(disease.getGenderRestriction(), gender);
        scores.put("genderScore", genderScore);

        // điểm tuổi
        double ageScore = calculateAgeScore(
                disease.getAgeMin(),
                disease.getAgeMax(),
                age
        );
        scores.put("ageScore", ageScore);

        // điểm negative matching (triệu chứng input k có trong bệnh)
        Set<Long> diseaseSymptomIds = disease.getSymptomDiseases().stream()
                .map(sd -> sd.getSymptom().getId())
                .collect(Collectors.toSet());
        long nonMatchingCount = inputSymptomIds.stream()
                .filter(id -> !diseaseSymptomIds.contains(id))
                .count();
        double negativeScore = nonMatchingCount * 0.1;
        scores.put("negativeScore", negativeScore);

        return scores;
    }

    private double calculateFinalScore(Map<String, Double> scoreComponents) {
        // trọng số cho từng phần
        final double MATCHING_RATIO_WEIGHT = 0.4;
        final double KEY_SYMPTOM_WEIGHT = 0.3;
        final double GENDER_WEIGHT = 0.15;
        final double AGE_WEIGHT = 0.15;

        return scoreComponents.get("matchingRatio") * MATCHING_RATIO_WEIGHT +
                scoreComponents.get("keySymptomScore") * KEY_SYMPTOM_WEIGHT +
                scoreComponents.get("genderScore") * GENDER_WEIGHT +
                scoreComponents.get("ageScore") * AGE_WEIGHT -
                scoreComponents.get("negativeScore") * 0.2;
    }

    private double calculateGenderScore(String diseaseGender, String patientGender) {
        if ("ALL".equals(diseaseGender)) return 1.0;
        return diseaseGender.equals(patientGender) ? 1.0 : 0.0;
    }

    private double calculateAgeScore(Integer minAge, Integer maxAge, int age) {
        // nếu k có giới hạn tuổi
        if (minAge == null || maxAge == null) return 1.0;

        // nếu nằm trong khoảng tuổi
        if (age >= minAge && age <= maxAge) return 1.0;

        // nếu ngoài khoảng
        int closest = age < minAge ? minAge : maxAge;
        int ageDiff = Math.abs(age - closest);

        // giảm 0.2 cho mỗi 5 tuổi chênh lệch
        return Math.max(0.0, 1.0 - (ageDiff / 5.0) * 0.2);
    }

}
