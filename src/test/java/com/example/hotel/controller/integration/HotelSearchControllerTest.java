package com.example.hotel.controller.integration;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hotel.model.Address;
import com.example.hotel.model.ArrivalTime;
import com.example.hotel.model.Contacts;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();
    }

    @Test
    void testSearchHotels_ShouldReturnHotels_WhenHotelsExist() throws Exception {
        hotelRepository.save(Hotel.builder()
                .name("Hotel 1")
                .brand("Brand A")
                .description("Desc 1")
                .address(Address.builder().city("City A").county("County A").postCode("12345").street("Street A").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build())
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build())
                .amenities(List.of("WiFi"))
                .build());

        hotelRepository.save(Hotel.builder()
                .name("Hotel 2")
                .brand("Brand B")
                .description("Desc 2")
                .address(Address.builder().city("City B").county("County B").postCode("67890").street("Street B").houseNumber(2).build())
                .contacts(Contacts.builder().phone("0987654321").email("test2@example.com").build())
                .arrivalTime(ArrivalTime.builder().checkIn("15:00").checkOut("11:00").build())
                .amenities(List.of("Pool"))
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .param("city", "City A"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Hotel 1")));
    }

    @Test
    void testSearchHotels_ShouldReturnEmptyList_WhenNoHotelsMatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .param("city", "Nonexistent City"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }
}
