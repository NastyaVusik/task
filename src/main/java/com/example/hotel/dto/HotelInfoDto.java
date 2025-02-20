package com.example.hotel.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelInfoDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
