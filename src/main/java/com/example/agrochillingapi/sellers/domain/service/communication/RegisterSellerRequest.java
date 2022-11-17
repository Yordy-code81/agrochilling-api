package com.example.agrochillingapi.sellers.domain.service.communication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class RegisterSellerRequest {

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
    @Size(max = 60)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String farm_address;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String phone;

    private Set<String> roles;
}
