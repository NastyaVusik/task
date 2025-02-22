package com.example.hotel.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Hotel name is required")
    private String name;
    private String description;
    @NotBlank
    private String brand;

    @Embedded
    private Address address;

    @Embedded
    private Contacts contacts;

    @ElementCollection
    private List<String> amenities;

    @Embedded
    private ArrivalTime arrivalTime;
}
