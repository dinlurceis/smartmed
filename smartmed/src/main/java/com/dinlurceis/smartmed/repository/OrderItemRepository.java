package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.OrderItem;
import com.dinlurceis.smartmed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Boolean existsByUserAndMedicineIdAndOrder_orderStatus(User user, Long medicineId, OrderStatus orderOrderStatus);


    List<OrderItem> findByUserAndMedicineAndOrder_orderStatus(
            User user,
            Medicine medicine,
            OrderStatus status
    );

}
