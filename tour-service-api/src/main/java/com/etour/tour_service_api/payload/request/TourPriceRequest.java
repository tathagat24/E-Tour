package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class TourPriceRequest {
    @NotNull(message = "SinglePersonPrice cannot be empty or null")
    private Double singlePersonPrice;
    @NotNull(message = "TwinSharingPrice cannot be empty or null")
    private Double twinSharingPrice;
    @NotNull(message = "ExtraPersonPrice cannot be empty or null")
    private Double extraPersonPrice;
    @NotNull(message = "ChildWithBedPrice cannot be empty or null")
    private Double childWithBedPrice;
    @NotNull(message = "ChildWithoutBedPrice cannot be empty or null")
    private Double childWithoutBedPrice;
}
