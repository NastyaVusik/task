package com.example.hotel.service.unit;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hotel.model.Address;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.service.HotelStatisticsService;

@ExtendWith(MockitoExtension.class)
public class HotelStatisticsServiceTest {

    @Mock
    HotelRepository hotelRepository;
    @InjectMocks
    HotelStatisticsService hotelStatisticsService;

    List<Hotel> hotels;

    @BeforeEach
    void setUp() {
        Address address1 = Address.builder().city("City1").county("County1").build();
        Address address2 = Address.builder().city("City2").county("County2").build();

        hotels = List.of(
                Hotel.builder().id(1L).name("Hotel1").brand("Brand1").address(address1).amenities(List.of("WiFi", "Pool")).build(),
                Hotel.builder().id(2L).name("Hotel2").brand("Brand1").address(address2).amenities(List.of("Gym", "Parking")).build(),
                Hotel.builder().id(3L).name("Hotel3").brand("Brand2").address(address1).amenities(List.of("WiFi", "Spa")).build()
        );
    }

    @Test
    void getHistogram_ShouldReturnCorrectCount_ByBrand() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);

        //      When
        Map<String, Long> histogram = hotelStatisticsService.getHistogram("brand");

        //      Then
        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("Brand1"));
        assertEquals(1, histogram.get("Brand2"));
        verify(hotelRepository).findAll();
    }

    @Test
    void getHistogram_ShouldReturnCorrectCount_ByCity() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);

        //      When
        Map<String, Long> histogram = hotelStatisticsService.getHistogram("city");

        //      Then
        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("City1"));
        assertEquals(1, histogram.get("City2"));
        verify(hotelRepository).findAll();
    }

    @Test
    void getHistogram_ShouldReturnCorrectCount_ByCounty() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);

        //      When
        Map<String, Long> histogram = hotelStatisticsService.getHistogram("county");

        //      Then
        assertEquals(2, histogram.size());
        assertEquals(2, histogram.get("County1"));
        assertEquals(1, histogram.get("County2"));
        verify(hotelRepository).findAll();
    }

    @Test
    void getHistogram_ShouldReturnCorrectCount_ByAmenities() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);

        //      When
        Map<String, Long> histogram = hotelStatisticsService.getHistogram("amenities");

        //      Then
        assertEquals(5, histogram.size());
        assertEquals(2, histogram.get("WiFi"));
        assertEquals(1, histogram.get("Pool"));
        assertEquals(1, histogram.get("Gym"));
        assertEquals(1, histogram.get("Spa"));
        verify(hotelRepository).findAll();
    }

    @Test
    void getHistogram_ShouldThrowException_ForInvalidParam() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);

        //      When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelStatisticsService.getHistogram("invalid_param");
        });

        //      Then
        assertEquals("Invalid parameter: invalid_param", exception.getMessage());
        verify(hotelRepository).findAll();
    }
}
