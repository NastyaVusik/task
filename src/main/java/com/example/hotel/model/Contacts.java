package com.example.hotel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

import jakarta.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
public class Contacts {
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
