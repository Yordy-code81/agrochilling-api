package com.example.agrochillingapi.sellers.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuthenticateSellerResource {

    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String farm_address;
    private String phone;
    private List<String> roles;
    private String token;
}
