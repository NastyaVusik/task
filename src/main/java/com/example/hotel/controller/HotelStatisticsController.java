package com.example.hotel.controller;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.service.HotelStatisticsService;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Tag(name = "Create new scratch file from selection", description = "Help to get statistics about hotels")
public class HotelStatisticsController {

    private final HotelStatisticsService hotelStatisticsService;

    @Operation(summary = "Get hotels statistics",
            description = "Get summary statistics about hotels according their parameters")
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelStatisticsService.getHistogram(param);
    }
}