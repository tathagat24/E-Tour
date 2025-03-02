package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeparturesRequest {
    @NotNull(message = "Tour Id can not be empty or null")
    private Long tourId;
    @NotNull(message = "Departures can not be empty or null")
    private List<DepartureRequest> departures;
}
