package com.etour.tour_service_api.entity;

import com.etour.tour_service_api.enumeration.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class BookingEntity extends Auditable {
    private LocalDateTime bookingDate;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;
    private Double totalPrice;

    @ManyToOne(targetEntity = TourEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "tourId")
    private TourEntity tourEntity;

    @ManyToOne(targetEntity = DepartureEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "departureId")
    private DepartureEntity departureEntity;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "userId")
    private UserEntity userEntity;
}

