package com.example.hotel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.repository.HotelRepository;

@Service
public class HotelSearchService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelInfoDto> searchHotels(String name, String brand, String city, String county, List<String> amenities) {
        return hotelRepository.findAll().stream()
            .filter(hotel -> (name == null || hotel.getName().equalsIgnoreCase(name)) &&
                             (brand == null || hotel.getBrand().equalsIgnoreCase(brand)) &&
                             (city == null || hotel.getAddress().getCity().equalsIgnoreCase(city)) &&
                             (county == null || hotel.getAddress().getCounty().equalsIgnoreCase(county)) &&
                             (amenities == null || hotel.getAmenities().containsAll(amenities)))
            .map(hotel -> HotelMapper.toDto(hotel))
            .collect(Collectors.toList());
    }
}
