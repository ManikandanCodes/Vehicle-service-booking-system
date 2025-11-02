package com.vehicle.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle.vehicle_service.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
