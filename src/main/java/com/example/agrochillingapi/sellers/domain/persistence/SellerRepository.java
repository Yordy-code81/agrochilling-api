package com.example.agrochillingapi.sellers.domain.persistence;

import com.example.agrochillingapi.sellers.domain.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select d from Seller d where d.email = ?1")
    Optional<Seller> findByEmail(String email);

    Boolean existsByEmail(String email);
}
