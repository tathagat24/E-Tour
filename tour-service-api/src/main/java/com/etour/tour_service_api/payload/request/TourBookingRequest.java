package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourBookingRequest {
    @NotNull(message = "Tour id cannot be empty or null")
    private Long tourId;
    @NotNull(message = "User id cannot be empty or null")
    private Long userId;
    @NotNull(message = "Departure cannot be empty or null")
    private Long departureId;
    @NotNull(message = "Total price cannot be empty or null")
    private Double totalPrice;
    @NotNull(message = "Passengers cannot be empty or null")
    private List<PassengerRequest> passengers;
}
