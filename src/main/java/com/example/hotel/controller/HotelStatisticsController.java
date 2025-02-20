package com.example.hotel.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.service.HotelStatisticsService;

@RestController
@RequestMapping("/property-view/histogram")
@RequiredArgsConstructor
public class HotelStatisticsController {

    private final HotelStatisticsService hotelStatisticsService;

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelStatisticsService.getHistogram(param);
    }
}