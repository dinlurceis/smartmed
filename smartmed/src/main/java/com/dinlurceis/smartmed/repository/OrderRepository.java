package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Order;
import com.dinlurceis.smartmed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
