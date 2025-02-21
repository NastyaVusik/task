package com.example.hotel.unit;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.Address;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.service.HotelSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelSearchServiceTest {

    @Mock
    HotelRepository hotelRepository;
    @Mock
    HotelMapper hotelMapper;
    @InjectMocks
    HotelSearchService hotelSearchService;

    List<Hotel> hotelsList;
    List<HotelInfoDto> hotelsInfoDtoList;

    @BeforeEach
    public void setUp() {
        hotelsList = new ArrayList<>();
        hotelsInfoDtoList = new ArrayList<>();
        Address address1 = Address.builder()
                .city("City1")
                .county("County1")
                .build();
        Address address2 = Address.builder()
                .city("City2")
                .county("County2")
                .build();
        Address address3 = Address.builder()
                .city("City3")
                .county("County3")
                .build();
        Hotel hotel1 = Hotel.builder()
                .name("Hotel1")
                .description("Description1")
                .brand("Brand1")
                .address(address1)
                .amenities(List.of("swimming pool", "parking", "laundry", "restaurant", "massage"))
                .build();
        Hotel hotel2 = Hotel.builder()
                .name("Hotel2")
                .description("Description2")
                .brand("Brand2")
                .address(address2)
                .amenities(List.of("swimming pool", "parking", "laundry"))
                .build();
        Hotel hotel3 = Hotel.builder()
                .name("Hotel3")
                .description("Description3")
                .brand("Brand3")
                .address(address3)
                .amenities(List.of("parking", "restaurant"))
                .build();
        HotelInfoDto hotelInfoDto1 = HotelInfoDto.builder()
                .name(hotel1.getName())
                .description(hotel1.getDescription())
                .address(String.valueOf(hotel1.getAddress()))
                .build();
        HotelInfoDto hotelInfoDto2 = HotelInfoDto.builder()
                .name(hotel2.getName())
                .description(hotel2.getDescription())
                .address(String.valueOf(hotel2.getAddress()))
                .build();
        HotelInfoDto hotelInfoDto3 = HotelInfoDto.builder()
                .name(hotel3.getName())
                .description(hotel3.getDescription())
                .address(String.valueOf(hotel3.getAddress()))
                .build();
        hotelsList.add(hotel1);
        hotelsList.add(hotel2);
        hotelsList.add(hotel3);
        hotelsInfoDtoList.add(hotelInfoDto1);
        hotelsInfoDtoList.add(hotelInfoDto2);
        hotelsInfoDtoList.add(hotelInfoDto3);
    }

    @Test
    void searchHotels_shouldReturnMatchingDto_whenSearchByCityAndAmenity() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotelsList);
        when(hotelMapper.toHotelInfoDto(any(Hotel.class)))
                .thenAnswer(invokation -> {
                    Hotel hotel = invokation.getArgument(0);
                    return HotelInfoDto.builder()
                            .name(hotel.getName())
                            .description(hotel.getDescription())
                            .address(hotel.getAddress().getCity() + ", " + hotel.getAddress().getCounty())
                            .build();
                });

        //      When
        List<HotelInfoDto> searchHotels = hotelSearchService.searchHotels(null, null, "City2", null, List.of("swimming pool", "parking", "laundry"));

        //      Then
        assertEquals(1, searchHotels.size());
        assertEquals("Hotel2", searchHotels.get(0).getName());
        verify(hotelRepository).findAll();
        verify(hotelMapper).toHotelInfoDto(any(Hotel.class));
    }

    @Test
    void searchHotels_ShouldReturnMatchingDto_whenSearchByBrand() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotelsList);
        when(hotelMapper.toHotelInfoDto(any(Hotel.class)))
                .thenAnswer(invocation -> {
                    Hotel hotel = invocation.getArgument(0);
                    return HotelInfoDto.builder()
                            .name(hotel.getName())
                            .description(hotel.getDescription())
                            .address(hotel.getAddress().getCity() + ", " + hotel.getAddress().getCounty())
                            .build();
                });

        //      When
        List<HotelInfoDto> results = hotelSearchService.searchHotels(null, "Brand2", null, null, null);

        //      Then
        assertEquals(1, results.size());
        assertEquals("Hotel2", results.get(0).getName());

        verify(hotelRepository).findAll();
        verify(hotelMapper).toHotelInfoDto(any(Hotel.class));
    }

    @Test
    void searchHotels_shouldReturnEmptyList_whenNoMatchName() {

        //      Given
        when(hotelRepository.findAll()).thenReturn(hotelsList);

        //      When
        List<HotelInfoDto> searchHotels = hotelSearchService.searchHotels("Hotel4", null, null, null, null);

        //      Then
        assertEquals(0, searchHotels.size());
        verify(hotelRepository).findAll();
        verify(hotelMapper, never()).toHotelInfoDto(any(Hotel.class));

    }
}

