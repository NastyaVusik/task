package com.example.hotel.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HotelInfoDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
