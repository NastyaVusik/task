package com.example.hotel.model;

import lombok.*;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
