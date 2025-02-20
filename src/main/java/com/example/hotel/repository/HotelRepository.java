package com.example.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotel.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
