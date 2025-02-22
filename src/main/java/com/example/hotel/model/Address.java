package com.example.hotel.model;

import lombok.*;

import jakarta.persistence.Embeddable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return houseNumber + " " + street + ", " + city + ", " + postCode + ", " + county;
    }
}
