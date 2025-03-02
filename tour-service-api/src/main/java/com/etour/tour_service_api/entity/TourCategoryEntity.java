package com.etour.tour_service_api.entity;

import com.etour.tour_service_api.enumeration.TourCategory;
import jakarta.persistence.*;
import lombok.*;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour_categories")
public class TourCategoryEntity extends Auditable {
    @Enumerated(value = EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private TourCategory categoryName;
    private String imageUrl;
}

