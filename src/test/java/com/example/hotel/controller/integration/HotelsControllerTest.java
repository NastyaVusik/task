//package com.example.hotel.controller.integration;
//
//import com.example.hotel.model.Address;
//import com.example.hotel.model.Hotel;
//import com.example.hotel.repository.HotelRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class HotelsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private HotelRepository hotelRepository;
//
//    @BeforeEach
//    void setUp() {
//        hotelRepository.deleteAll();
//    }
//
//    @Test
//    void testGetAllHotels_ShouldReturnEmptyList_WhenNoHotelsExist() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/hotels"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("[]"));
//    }
//
//    @Test
//    void testCreateHotel_ShouldReturnHotel_WhenValidInput() throws Exception {
//        Hotel hotel = Hotel.builder()
//                .name("Hotel Test")
//                .brand("Brand Test")
//                .description("Test Description")
//                .address(Address.builder().city("City1").county("County1").build())
//                .amenities(List.of("WiFi", "Pool"))
//                .build();
//
//        String hotelJson = objectMapper.writeValueAsString(hotel);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/hotels")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(hotelJson))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hotel Test"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Brand Test"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
//    }
//
//    @Test
//    void testGetHotelById_ShouldReturnHotel_WhenHotelExists() throws Exception {
//        Hotel hotel = hotelRepository.save(Hotel.builder()
//                .name("Hotel 1")
//                .brand("Brand A")
//                .description("Desc 1")
//                .address(Address.builder().city("City A").county("County A").build())
//                .amenities(List.of("WiFi"))
//                .build());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", hotel.getId()))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hotel 1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Brand A"));
//    }
//
//    @Test
//    void testAddAmenities_ShouldReturnUpdatedHotel_WhenHotelExists() throws Exception {
//        Hotel hotel = hotelRepository.save(Hotel.builder()
//                .name("Hotel 1")
//                .brand("Brand A")
//                .description("Desc 1")
//                .address(Address.builder().city("City A").county("County A").build())
//                .amenities(new ArrayList<>(List.of("WiFi")))
//                .build());
//
//        List<String> newAmenities = List.of("Gym", "Spa");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{id}/amenities", hotel.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newAmenities)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.amenities", hasSize(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.amenities", hasItems("WiFi", "Gym", "Spa")));
//    }
//
//    @Test
//    void testGetHotelById_ShouldReturnNotFound_WhenHotelDoesNotExist() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", 999L))
//                .andExpect(status().isOk()) // Since Optional<Hotel> is returned, this won't be 404 but empty
//                .andExpect(MockMvcResultMatchers.content().string(""));
//    }
//}

