package com.example.agrochillingapi.sellers.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerResource {

    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String farm_address;
    private String phone;
    private List<String> roles;
}
