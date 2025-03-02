package com.etour.tour_service_api.service;

import com.etour.tour_service_api.dto.TourSubcategoryDto;
import com.etour.tour_service_api.enumeration.TourCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

public interface TourSubCategoryService {
    void createTourSubCategory(TourCategory categoryName, String subCategoryName, MultipartFile imageFile);
    List<TourSubcategoryDto> getAllTourSubcategories();
    List<TourSubcategoryDto> getAllTourSubcategoriesByTourCategoryId(Long tourCategoryId);
}
