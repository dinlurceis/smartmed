package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.Review;
import com.dinlurceis.smartmed.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndMedicine(User user, Medicine medicine);

    Page<Review> findByMedicineId(Long medicineId, Pageable pageable);
}
