package com.example.hotel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.example.hotel.dto.HotelInfoDto;
import com.example.hotel.model.Hotel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {

    @Mapping(target = "address", source = ".", qualifiedByName = "mapAddressToString")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelInfoDto toHotelInfoDto(Hotel hotel);

    @Named("mapAddressToString")
    default String mapAddressToString(Hotel hotel) {
        return (hotel == null || hotel.getAddress() == null) ? null : hotel.getAddress().toString();
    }
}
