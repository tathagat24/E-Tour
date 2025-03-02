package com.etour.tour_service_api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "tour_prices")
public class TourPriceEntity extends Auditable {
    private Double singlePersonPrice;
    private Double twinSharingPrice;
    private Double extraPersonPrice;
    private Double childWithBedPrice;
    private Double childWithoutBedPrice;
    @OneToOne(targetEntity = TourEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "tourId")
    private TourEntity tourEntity;
}
