package com.example.hotel.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalTime {

    @NotBlank
    private String checkIn;
    private String checkOut;
}
