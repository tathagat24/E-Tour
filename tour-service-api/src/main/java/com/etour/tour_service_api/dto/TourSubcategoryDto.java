package com.etour.tour_service_api.dto;

import com.etour.tour_service_api.enumeration.TourCategory;
import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@Data
@Builder
public class TourSubcategoryDto {
    private Long id;
    private String referenceId;
    private String subCategoryName;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
    private Long tourCategoryId;
}
