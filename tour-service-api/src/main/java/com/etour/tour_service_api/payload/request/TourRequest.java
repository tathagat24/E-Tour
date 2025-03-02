package com.etour.tour_service_api.payload.request;

import com.etour.tour_service_api.entity.TourPriceEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 30-01-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourRequest {
    @NotEmpty(message = "Tour name cannot be empty or null")
    private String tourName;
    @NotEmpty(message = "Description cannot be empty or null")
    private String description;
    @NotEmpty(message = "Duration cannot be empty or null")
    private String duration;
    @NotEmpty(message = "Start Date cannot be empty or null")
    private String startDate;
    @NotEmpty(message = "End Date cannot be empty or null")
    private String endDate;
    @NotNull(message = "TourSubcategoryId cannot be empty or null")
    private Long tourSubcategoryId;
    @NotNull(message = "Tour Price cannot be empty or null")
    private TourPriceRequest tourPriceRequest;
    @NotNull(message = "Itineraries cannot be empty or null")
    private List<ItineraryRequest> itineraryRequests;
}
