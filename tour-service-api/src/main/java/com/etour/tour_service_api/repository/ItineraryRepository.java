package com.etour.tour_service_api.repository;

import com.etour.tour_service_api.entity.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {
}
