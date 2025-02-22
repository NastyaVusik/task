package com.example.hotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.model.Hotel;
import com.example.hotel.service.HotelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "HotelsController", description = "Help to operate with hotels")
public class HotelsController {

    private final HotelService hotelService;

    @Operation(summary = "Get all hotels",
            description = "Get all hotels from the base")
    @GetMapping
    public List<HotelInfoDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get hotel by id",
            description = "Get hotel by curtain id")
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "Create new hotel",
            description = "Create new hotel with curtain parameters. And add it to the database")
    @PostMapping
    public HotelInfoDto createHotel(@Valid @RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @Operation(summary = "Add new amenities to the hotel",
            description = "Add a list of amenities to an existing hotel found by id")
    @PostMapping("/{id}/amenities")
    public Hotel addAmenities(@PathVariable Long id,
            @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
