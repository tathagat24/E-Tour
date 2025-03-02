package com.etour.tour_service_api.service;

import com.etour.tour_service_api.dto.TourCategoryDto;
import com.etour.tour_service_api.enumeration.TourCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

public interface TourCategoryService {
    void createTourCategory(TourCategory categoryName, MultipartFile imageFile);
    List<TourCategoryDto> getAllTourCategories();
    TourCategoryDto getTourCategoryByCategoryName(TourCategory tourCategory);
    TourCategoryDto updateTourCategory(Long categoryId, TourCategory categoryName, MultipartFile imageFile);
}
