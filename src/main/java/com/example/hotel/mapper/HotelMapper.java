package com.example.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.model.Hotel;

//@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {
    //    public static HotelInfoDto toDto(Hotel hotel) {
//        HotelInfoDto dto = new HotelInfoDto();
//        dto.setId(hotel.getId());
//        dto.setName(hotel.getName());
//        dto.setDescription(hotel.getDescription());
//        dto.setAddress(hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " + hotel.getAddress().getCity() + ", " + hotel.getAddress().getPostCode() + ", " + hotel.getAddress().getCounty());
//        dto.setPhone(hotel.getContacts().getPhone());
//        return dto;
//    }
    @Mapping(target = "address", source = ".", qualifiedByName = "mapAddressToString")
    HotelInfoDto toHotelInfoDto(Hotel hotel);

    @Named("mapAddressToString")
    default String mapAddressToString(Hotel hotel) {
        return (hotel == null || hotel.getAddress() == null) ? null : hotel.getAddress().toString();
    }
}
