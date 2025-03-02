package com.etour.tour_service_api.repository;

import com.etour.tour_service_api.entity.TourCategoryEntity;
import com.etour.tour_service_api.enumeration.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategoryEntity, Long> {
    Optional<TourCategoryEntity> findByCategoryName(TourCategory categoryName);
}
