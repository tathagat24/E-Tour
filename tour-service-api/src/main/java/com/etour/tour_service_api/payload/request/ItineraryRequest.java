package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 30-01-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItineraryRequest {
    @NotNull(message = "Day cannot be empty or null")
    private Integer day;
    @NotEmpty(message = "ItineraryName cannot be empty or null")
    private String itineraryName;
    @NotEmpty(message = "Description cannot be empty or null")
    private String description;
}
