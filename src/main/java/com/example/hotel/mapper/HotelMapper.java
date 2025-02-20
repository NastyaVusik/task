package com.example.hotel.mapper;

import org.springframework.stereotype.Component;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.model.Hotel;

@Component
public class HotelMapper {
    public static HotelInfoDto toDto(Hotel hotel) {
        HotelInfoDto dto = new HotelInfoDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setAddress(hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " + hotel.getAddress().getCity() + ", " + hotel.getAddress().getPostCode() + ", " + hotel.getAddress().getCounty());
        dto.setPhone(hotel.getContacts().getPhone());
        return dto;
    }
}
