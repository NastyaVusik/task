package com.example.hotel.controller;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.model.Hotel;
import com.example.hotel.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/property-view/hotels")
@RequiredArgsConstructor
public class HotelsController {

    private final HotelService hotelService;

    @GetMapping
    public List<HotelInfoDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Optional<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    public HotelInfoDto createHotel(@Valid @RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PostMapping("/{id}/amenities")
    public Hotel addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
