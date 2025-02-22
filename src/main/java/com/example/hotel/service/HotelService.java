package com.example.hotel.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.exception.HotelNotFoundException;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final Validator validator;

    public List<HotelInfoDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotelMapper::toHotelInfoDto).collect(Collectors.toList());
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    public HotelInfoDto createHotel(Hotel hotel) {
        return hotelMapper.toHotelInfoDto(hotelRepository.save(hotel));
    }

    public Hotel addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        hotel.getAmenities().addAll(amenities);
        validateHotel(hotel);
        return hotelRepository.save(hotel);
    }

    private void validateHotel(Hotel hotel) {
        var violations = validator.validate(hotel);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public List<Hotel> searchHotels(String name, String brand, String city, String county, List<String> amenities) {
        return hotelRepository.findAll().stream()
                .filter(hotel -> (name == null || hotel.getName().equalsIgnoreCase(name))
                && (brand == null || hotel.getBrand().equalsIgnoreCase(brand))
                && (city == null || hotel.getAddress().getCity().equalsIgnoreCase(city))
                && (county == null || hotel.getAddress().getCounty().equalsIgnoreCase(county))
                && (amenities == null || hotel.getAmenities().containsAll(amenities)))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getHotelStatistics(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        switch (param.toLowerCase()) {
            case "brand":
                return hotels.stream().collect(Collectors.groupingBy(Hotel::getBrand, Collectors.summingInt(e -> 1)));
            case "city":
                return hotels.stream().collect(Collectors.groupingBy(h -> h.getAddress().getCity(), Collectors.summingInt(e -> 1)));
            case "county":
                return hotels.stream().collect(Collectors.groupingBy(h -> h.getAddress().getCounty(), Collectors.summingInt(e -> 1)));
            case "amenities":
                return hotels.stream()
                        .flatMap(h -> h.getAmenities().stream())
                        .collect(Collectors.groupingBy(a -> a, Collectors.summingInt(e -> 1)));
            default:
                throw new IllegalArgumentException("Invalid parameter: " + param);
        }
    }
}
