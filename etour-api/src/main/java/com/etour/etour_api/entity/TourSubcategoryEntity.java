package com.etour.etour_api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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
@Table(name = "tour_subcategories")
public class TourSubcategoryEntity extends Auditable {
    @Column(length = 100, nullable = false)
    private String subCategoryName;
    private String imageUrl;

    @ManyToOne(targetEntity = TourCategoryEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "tourCategoryId")
    private TourCategoryEntity tourCategoryEntity;

    @OneToMany(mappedBy = "tourSubcategoryEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonProperty(value = "tours")
    private List<TourEntity> tourEntities;
}
