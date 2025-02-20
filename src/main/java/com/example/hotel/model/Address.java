package com.example.hotel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
public class Address {
    @NotNull
    @Min(1)
    private int houseNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String county;
    @NotBlank
    private String postCode;
}
