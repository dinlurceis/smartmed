package com.dinlurceis.smartmed.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class SpecificationBuilder<T> {

    public Specification<T> build(Map<String, String> filters, String keyword, String... searchFields) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filters != null) {
                for (String field : filters.keySet()) {
                    String value = filters.get(field);

                    // trong khoang priceFrom, priceTo
                    if (field.endsWith("From")) {
                        String actualField = field.replace("From", "");
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.greaterThanOrEqualTo(root.get(actualField), parseToComparable(value)));
                    } else if (field.endsWith("To")) {
                        String actualField = field.replace("To", "");
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.lessThanOrEqualTo(root.get(actualField), parseToComparable(value)));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(field), value));
                    }
                }
            }

            if (keyword != null && !keyword.isEmpty() && searchFields != null) {
                Predicate keywordPredicate = criteriaBuilder.disjunction();
                for (String field : searchFields) {
                    keywordPredicate = criteriaBuilder.or(keywordPredicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + keyword.toLowerCase() + "%"));
                }
                predicate = criteriaBuilder.and(predicate, keywordPredicate);
            }

            return predicate;
        };
    }

    private Comparable parseToComparable(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
