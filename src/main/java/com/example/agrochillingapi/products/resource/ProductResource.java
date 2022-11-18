package com.example.agrochillingapi.products.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResource {

    private Long id;
    private String name;
    private Double amount;
    private String type;
    private String image;
    private Double price;
}
