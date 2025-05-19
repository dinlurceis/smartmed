package com.dinlurceis.smartmed.model;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonIgnore
    User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnore
    Address address;

    LocalDateTime orderTime;

    @NotNull
    String fullName;

    @NotNull
    String phoneNumber;

    String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Set<OrderItem> orderItems;

    OrderStatus orderStatus;

    Double totalPrice;

    Integer totalQuantity;

    @JsonIgnore
    public Double getTotalPrice() {
        Double totalPrice = 0.0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }
        return totalPrice;
    }

    @JsonIgnore
    public Integer getTotalQuantity() {
        Integer totalQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            totalQuantity += orderItem.getQuantity();
        }
        return totalQuantity;
    }
}