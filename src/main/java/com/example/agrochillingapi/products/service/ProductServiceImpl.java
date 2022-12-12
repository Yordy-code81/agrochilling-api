package com.example.agrochillingapi.products.service;

import com.example.agrochillingapi.products.domain.model.entity.Product;
import com.example.agrochillingapi.products.domain.persistence.ProductRepository;
import com.example.agrochillingapi.products.domain.service.ProductService;
import com.example.agrochillingapi.sellers.domain.persistence.SellerRepository;
import com.example.agrochillingapi.shared.exception.ResourceNotFoundException;
import com.example.agrochillingapi.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String ENTITY = "Product";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private Validator validator;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    @Override
    public List<Product> getAllBySellerId(Long sellerId) {
        var existingDirector = sellerRepository.findById(sellerId);

        if(existingDirector.isEmpty())
            throw new ResourceNotFoundException("Seller", sellerId);

        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public Product create(Long directorId, Product request) {
        Set<ConstraintViolation<Product>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return sellerRepository.findById(directorId).map(director -> {
            request.setSeller(director);
            return productRepository.save(request);
        }).orElseThrow(() -> new ResourceNotFoundException("Seller", directorId));
    }

    @Override
    public Product update(Long announcementId, Product request) {
        Set<ConstraintViolation<Product>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return productRepository.findById(announcementId).map(announcement ->
                productRepository.save(
                        announcement.withName(request.getName())
                                .withAmount(request.getAmount()))
                                .withType(request.getType())
                                .withImage(request.getImage())
                                .withPrice(request.getPrice())
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, announcementId));
    }

    @Override
    public ResponseEntity<?> delete(Long announcementId) {
        return productRepository.findById(announcementId).map(announcement -> {
            productRepository.delete(announcement);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, announcementId));
    }
}
