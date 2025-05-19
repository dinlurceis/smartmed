package com.dinlurceis.smartmed.model;

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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String reviewText;

    @Column(nullable = false)
    double rating;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    Medicine medicine;

    @OneToOne
    @JsonIgnore
    OrderItem orderItem;

    @ManyToOne
    @JsonIgnore
    User user;
}
