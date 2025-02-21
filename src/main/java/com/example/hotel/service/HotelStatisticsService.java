package com.example.hotel.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;

@Service
@RequiredArgsConstructor
public class HotelStatisticsService {

    private final HotelRepository hotelRepository;

    public Map<String, Long> getHistogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        switch (param.toLowerCase()) {
            case "brand":
                return hotels.stream().collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));
            case "city":
                return hotels.stream().collect(Collectors.groupingBy(h -> h.getAddress().getCity(), Collectors.counting()));
            case "county":
                return hotels.stream().collect(Collectors.groupingBy(h -> h.getAddress().getCounty(), Collectors.counting()));
            case "amenities":
                return hotels.stream()
                        .flatMap(h -> h.getAmenities().stream())
                        .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
            default:
                throw new IllegalArgumentException("Invalid parameter: " + param);
        }
    }
}
