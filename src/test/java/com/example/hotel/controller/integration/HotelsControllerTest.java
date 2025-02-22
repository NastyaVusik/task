package com.example.hotel.controller.integration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hotel.model.Address;
import com.example.hotel.model.ArrivalTime;
import com.example.hotel.model.Contacts;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();
    }

    @Test
    void testGetAllHotels_ShouldReturnEmptyList_WhenNoHotelsExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void testCreateHotel_ShouldReturnHotel_WhenValidInput() throws Exception {
        Hotel hotel = Hotel.builder()
                .name("Hotel Test")
                .brand("Brand Test")
                .description("Test Description")
                .address(Address.builder().city("City1").county("County1").postCode("12345").street("Street 1").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build()) // Added contacts
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build()) // Added arrivalTime
                .amenities(List.of("WiFi", "Pool"))
                .build();

        String hotelJson = objectMapper.writeValueAsString(hotel);

        mockMvc.perform(MockMvcRequestBuilders.post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hotelJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hotel Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testCreateHotel_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        Hotel hotel = Hotel.builder()
                .name("") // Invalid name
                .brand("Brand Test")
                .description("Test Description")
                .address(Address.builder().city("City1").county("County1").postCode("12345").street("Street 1").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build()) // Added contacts
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build()) // Added arrivalTime
                .amenities(List.of("WiFi", "Pool"))
                .build();

        String hotelJson = objectMapper.writeValueAsString(hotel);

        mockMvc.perform(MockMvcRequestBuilders.post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hotelJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetHotelById_ShouldReturnHotel_WhenHotelExists() throws Exception {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name("Hotel 1")
                .brand("Brand A")
                .description("Desc 1")
                .address(Address.builder().city("City A").county("County A").postCode("12345").street("Street A").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build()) // Added contacts
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build()) // Added arrivalTime
                .amenities(List.of("WiFi"))
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", hotel.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hotel 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Brand A"));
    }

    @Test
    void testAddAmenities_ShouldReturnUpdatedHotel_WhenHotelExists() throws Exception {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name("Hotel 1")
                .brand("Brand A")
                .description("Desc 1")
                .address(Address.builder().city("City A").county("County A").postCode("12345").street("Street A").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build()) // Added contacts
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build()) // Added arrivalTime
                .amenities(new ArrayList<>(List.of("WiFi")))
                .build());

        List<String> newAmenities = List.of("Gym", "Spa");

        mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{id}/amenities", hotel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAmenities)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amenities", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amenities", hasItems("WiFi", "Gym", "Spa")));
    }

    @Test
    void testAddAmenities_ShouldReturnBadRequest_WhenInvalidAmenities() throws Exception {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name("Hotel 1")
                .brand("Brand A")
                .description("Desc 1")
                .address(Address.builder().city("City A").county("County A").postCode("12345").street("Street A").houseNumber(1).build())
                .contacts(Contacts.builder().phone("1234567890").email("test@example.com").build()) // Added contacts
                .arrivalTime(ArrivalTime.builder().checkIn("14:00").checkOut("12:00").build()) // Added arrivalTime
                .amenities(new ArrayList<>(List.of("WiFi")))
                .build());

        List<String> newAmenities = List.of(""); // Invalid amenity

        mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{id}/amenities", hotel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAmenities)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetHotelById_ShouldReturnNotFound_WhenHotelDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", 999L))
                .andExpect(status().isNotFound()); // Changed to 404 Not Found
    }
}
