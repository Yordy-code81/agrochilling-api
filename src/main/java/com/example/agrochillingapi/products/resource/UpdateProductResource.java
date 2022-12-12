package com.example.agrochillingapi.products.resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateProductResource {

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String name;

    private Double amount;

    @NotNull
    @NotBlank
    @Size(max = 15)
    private String type;

    @NotNull
    @NotBlank
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String image;

    private Double price;
}
