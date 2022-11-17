package com.example.agrochillingapi.sellers.domain.service;

import com.example.agrochillingapi.security.domain.service.communication.AuthenticateRequest;
import com.example.agrochillingapi.sellers.domain.model.entity.Seller;
import com.example.agrochillingapi.sellers.domain.service.communication.RegisterSellerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SellerService extends UserDetailsService {
    List<Seller> getAll();
    Page<Seller> getAll(Pageable pageable);
    ResponseEntity<?> authenticate(AuthenticateRequest request);
    ResponseEntity<?> register(RegisterSellerRequest request);
    Seller getById(Long directorId);
    Seller update(Long directorId, Seller director);
    ResponseEntity<?> delete(Long directorId);
}
