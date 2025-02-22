package com.example.hotel.service.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.exception.HotelNotFoundException;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.Address;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.service.HotelService;

import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    HotelRepository hotelRepository;
    @Mock
    HotelMapper hotelMapper;
    @Mock
    Validator validator;
    @InjectMocks
    HotelService hotelService;

    List<Hotel> hotels;
    List<HotelInfoDto> hotelInfoDtos;
    Hotel hotel1;
    Hotel hotel2;
    HotelInfoDto hotelInfoDto1, hotelInfoDto2;

    @BeforeEach
    void setUp() {
        Address address1 = Address.builder().city("City1").county("County1").build();
        Address address2 = Address.builder().city("City2").county("County2").build();

        hotel1 = Hotel.builder()
                .id(1L)
                .name("Hotel1")
                .description("Description1")
                .brand("Brand1")
                .address(address1)
                .amenities(new ArrayList<>(List.of("swimming pool", "parking", "laundry", "restaurant", "massage")))
                .build();

        hotel2 = Hotel.builder()
                .id(2L)
                .name("Hotel2")
                .description("Description2")
                .brand("Brand2")
                .address(address2)
                .amenities(new ArrayList<>(List.of("swimming pool", "parking", "laundry")))
                .build();

        hotelInfoDto1 = HotelInfoDto.builder()
                .id(hotel1.getId())
                .name(hotel1.getName())
                .description(hotel1.getDescription())
                .address("City1, County1")
                .build();

        hotelInfoDto2 = HotelInfoDto.builder()
                .id(hotel2.getId())
                .name(hotel2.getName())
                .description(hotel2.getDescription())
                .address("City2, County2")
                .build();

        hotels = List.of(hotel1, hotel2);
        hotelInfoDtos = List.of(hotelInfoDto1, hotelInfoDto2);
    }

    @Test
    void getAllHotels_ShouldReturnListOfHotelInfoDto_whenFindAll() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.toHotelInfoDto(hotel1)).thenReturn(hotelInfoDto1);
        when(hotelMapper.toHotelInfoDto(hotel2)).thenReturn(hotelInfoDto2);

        //      When
        List<HotelInfoDto> result = hotelService.getAllHotels();

        assertEquals(2, result.size());
        assertEquals("Hotel1", result.get(0).getName());
        assertEquals("Hotel2", result.get(1).getName());

        verify(hotelRepository).findAll();
        verify(hotelMapper, times(2)).toHotelInfoDto(any(Hotel.class));
    }

    @Test
    void getHotelById_ShouldReturnHotel_WhenFound() {

        //      Given
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel1));

        //      When
        Hotel result = hotelService.getHotelById(1L);

        //      Then
        assertEquals("Hotel1", result.getName());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void getHotelById_ShouldReturnEmpty_WhenNotFound() {

        //      Given
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then: Expect exception
        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.getHotelById(99L);
        });
        assertNotNull(exception);

        // Verify that findById was called once
        verify(hotelRepository, times(1)).findById(99L);

    }

    @Test
    void createHotel_ShouldSaveAndReturnHotelInfoDto() {

        //      Given
        when(hotelRepository.save(hotel1)).thenReturn(hotel1);
        when(hotelMapper.toHotelInfoDto(hotel1)).thenReturn(hotelInfoDto1);

        //      When
        HotelInfoDto result = hotelService.createHotel(hotel1);

        //      Then
        assertNotNull(result);
        assertEquals("Hotel1", result.getName());
        verify(hotelRepository, times(1)).save(hotel1);
        verify(hotelMapper, times(1)).toHotelInfoDto(hotel1);
    }

    @Test
    void addAmenities_ShouldAddAmenitiesAndSaveHotel() {

        //      Given
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel1));
        when(hotelRepository.save(hotel1)).thenReturn(hotel1);
        when(validator.validate(hotel1)).thenReturn(Set.of());

        //      When
        List<String> newAmenities = List.of("Spa", "Bar");
        Hotel updatedHotel = hotelService.addAmenities(1L, newAmenities);

        //      Then
        assertNotNull(updatedHotel);
        assertTrue(updatedHotel.getAmenities().containsAll(newAmenities));
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelRepository, times(1)).save(hotel1);
    }

    @Test
    void addAmenities_ShouldReturnNotFound_WhenHotelDoesNotExist() {

        //      Given
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        //      When
        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.addAmenities(99L, List.of("Spa", "Bar"));
        });

        //      Then
        assertNotNull(exception);
        verify(hotelRepository, times(1)).findById(99L);
        verify(hotelRepository, never()).save(any(Hotel.class));
    }
}
