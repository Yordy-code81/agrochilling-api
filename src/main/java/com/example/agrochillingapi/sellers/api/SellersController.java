package com.example.agrochillingapi.sellers.api;

import com.example.agrochillingapi.security.domain.service.communication.AuthenticateRequest;
import com.example.agrochillingapi.sellers.domain.service.SellerService;
import com.example.agrochillingapi.sellers.domain.service.communication.RegisterSellerRequest;
import com.example.agrochillingapi.sellers.mapping.SellerMapper;
import com.example.agrochillingapi.sellers.resource.SellerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/sellers")
public class SellersController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerMapper mapper;

    @GetMapping
    public List<SellerResource> getAllSellers() { return mapper.modelListToResource(sellerService.getAll()); }

    @GetMapping("{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public SellerResource getSellerById(@PathVariable("sellerId") Long sellerId) {
        return mapper.toResource(sellerService.getById(sellerId));
    }
    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateRequest request) {
        return sellerService.authenticate(request);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterSellerRequest request) {
        return sellerService.register(request);
    }

    @DeleteMapping("{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteSeller(@PathVariable Long sellerId) {
        return sellerService.delete(sellerId);
    }
}
