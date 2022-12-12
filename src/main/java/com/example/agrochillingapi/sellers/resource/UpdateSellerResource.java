package com.example.agrochillingapi.sellers.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateSellerResource {

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String first_name;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String last_name;

    private int age;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String farm_address;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String phone;
}
