package com.etour.etour_api.entity;

import com.etour.etour_api.enumeration.TourCategory;
import jakarta.persistence.*;
import lombok.*;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
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
