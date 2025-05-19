package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}
