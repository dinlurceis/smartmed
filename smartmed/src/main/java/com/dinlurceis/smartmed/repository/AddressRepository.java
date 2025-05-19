package com.dinlurceis.smartmed.repository;

import com.dinlurceis.smartmed.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("update Address a set a.address = ?1, a.city = ?2, a.state = ?3")
    int updateAddressAndCityAndStateBy(String address, String city, String state);
}
