package com.example.hotel.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
