package com.example.hotel.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.service.HotelSearchService;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "HotelSearchController", description = "Help to search hotels by curtain parameters")
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;

    @Operation(summary = "Search required hotels",
            description = "Help to find hotels by curtain parameters")
    @GetMapping
    public List<HotelInfoDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String county,
            @RequestParam(required = false) List<String> amenities) {
        return hotelSearchService.searchHotels(name, brand, city, county, amenities);
    }
}