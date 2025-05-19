package com.dinlurceis.smartmed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    Order order;

    @ManyToOne
    @JsonIgnore
    Medicine medicine;

    Integer quantity;

    Double price;

    @ManyToOne
    @JsonIgnore
    User user;

    @OneToOne
    @JsonIgnore
    Review review;
}
