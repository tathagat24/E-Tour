package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartureRequest {
    @NotEmpty(message = "Start Date cannot be empty or null")
    private String startDate;
    @NotEmpty(message = "End Date cannot be empty or null")
    private String endDate;
}