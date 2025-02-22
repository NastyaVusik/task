package com.example.hotel.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embeddable;

import jakarta.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
public class ArrivalTime {
    @NotBlank
    private String checkIn;
    private String checkOut;
}
