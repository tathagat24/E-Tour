package com.etour.tour_service_api.repository;

import com.etour.tour_service_api.entity.DepartureEntity;
import com.etour.tour_service_api.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

@Repository
public interface DepartureRepository extends JpaRepository<DepartureEntity, Long> {
    List<DepartureEntity> findAllByTourEntity(TourEntity tourEntity);
}