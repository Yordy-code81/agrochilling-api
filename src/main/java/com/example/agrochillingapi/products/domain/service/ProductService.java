package com.example.agrochillingapi.products.domain.service;

import com.example.agrochillingapi.products.domain.model.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    List<Product> getAllBySellerId(Long sellerId);
    Product create(Long directorId, Product request);
    Product update(Long announcementId, Product request);
    ResponseEntity<?> delete(Long announcementId);
}
