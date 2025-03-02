package com.etour.etour_api.repository;

import com.etour.etour_api.entity.TourCategoryEntity;
import com.etour.etour_api.enumeration.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategoryEntity, Long> {
    Optional<TourCategoryEntity> findByCategoryName(TourCategory categoryName);
}
