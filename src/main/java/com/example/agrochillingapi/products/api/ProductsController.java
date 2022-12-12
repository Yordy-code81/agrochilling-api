package com.example.agrochillingapi.products.api;

import com.example.agrochillingapi.products.domain.service.ProductService;
import com.example.agrochillingapi.products.mapping.ProductMapper;
import com.example.agrochillingapi.products.resource.CreateProductResource;
import com.example.agrochillingapi.products.resource.ProductResource;
import com.example.agrochillingapi.products.resource.UpdateProductResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('SELLER')")
public class ProductsController {

    private final ProductService productService;

    private final ProductMapper mapper;

    public ProductsController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("sellers/{sellerId}/products")
    public List<ProductResource> getAllProductsBySellerId(@PathVariable Long sellerId) {
        return mapper.modelListToResource(productService.getAllBySellerId(sellerId));
    }

    @GetMapping("products")
    public List<ProductResource> getAll() {
        return mapper.modelListToResource(productService.getAll());
    }

    @PostMapping("sellers/{sellerId}/products")
    public ProductResource createProduct(@PathVariable Long sellerId, @RequestBody CreateProductResource request) {
        return mapper.toResource(productService.create(sellerId, mapper.toModel(request)));
    }

    @PutMapping("products/{productId}")
    public ProductResource updateProduct(@PathVariable Long productId, @RequestBody UpdateProductResource request) {
        return mapper.toResource(productService.update(productId, mapper.toModel(request)));
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return productService.delete(productId);
    }

}
