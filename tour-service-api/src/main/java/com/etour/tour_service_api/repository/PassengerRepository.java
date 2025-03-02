package com.etour.tour_service_api.repository;

import com.etour.tour_service_api.entity.BookingEntity;
import com.etour.tour_service_api.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
     List<PassengerEntity> findAllByBookingEntity(BookingEntity bookingEntity);
}
