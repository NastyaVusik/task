package com.example.hotel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.exception.HotelNotFoundException;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

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
}
