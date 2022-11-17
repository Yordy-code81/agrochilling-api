package com.example.agrochillingapi.sellers.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("sellerMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public SellerMapper sellerMapper() { return new SellerMapper(); }
}
