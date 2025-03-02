package com.etour.etour_api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
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
@Table(name = "tours")
public class TourEntity extends Auditable {
    @Column(unique = true, nullable = false, updatable = false)
    private String tourId;
    @Column(length = 100, nullable = false)
    private String tourName;
    private String imageUrl;
    @Column(nullable = false)
    private String description;
    @Column(length = 50, nullable = false)
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(targetEntity = TourSubcategoryEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_subcategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "tourSubcategoryId")
    private TourSubcategoryEntity tourSubcategoryEntity;

    @OneToMany(mappedBy = "tourEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonProperty(value = "itineraries")
    private List<ItineraryEntity> itineraryEntities;

    @OneToOne(mappedBy = "tourEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonProperty(value = "tourPrice")
    private TourPriceEntity tourPriceEntity;
}
