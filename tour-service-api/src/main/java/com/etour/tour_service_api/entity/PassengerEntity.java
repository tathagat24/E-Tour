package com.etour.tour_service_api.entity;

import com.etour.tour_service_api.enumeration.Gender;
import com.etour.tour_service_api.enumeration.PassengerType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
@Table(name = "passengers")
public class PassengerEntity extends Auditable {
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String middleName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(length = 100)
    private String email;
    @Column(length = 20)
    private String phone;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Integer age;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PassengerType passengerType;
    @Column(nullable = false)
    private Double passengerCost;

    @ManyToOne(targetEntity = TourEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "tourId")
    private TourEntity tourEntity;

    @ManyToOne(targetEntity = BookingEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "bookingId")
    private BookingEntity bookingEntity;
}

