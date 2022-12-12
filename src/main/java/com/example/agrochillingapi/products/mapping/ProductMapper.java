package com.example.agrochillingapi.products.mapping;

import com.example.agrochillingapi.products.domain.model.entity.Product;
import com.example.agrochillingapi.products.resource.CreateProductResource;
import com.example.agrochillingapi.products.resource.ProductResource;
import com.example.agrochillingapi.products.resource.UpdateProductResource;
import com.example.agrochillingapi.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ProductMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public ProductResource toResource(Product model) { return mapper.map(model, ProductResource.class); }

    public Page<ProductResource> modelListToPage(List<Product> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ProductResource.class), pageable, modelList.size());
    }

    public List<ProductResource> modelListToResource(List<Product> modelList){return mapper.mapList(modelList, ProductResource.class); }

    public Product toModel(CreateProductResource resource) {
        return mapper.map(resource, Product.class);
    }

    public Product toModel(UpdateProductResource resource) {
        return mapper.map(resource, Product.class);
    }
}
