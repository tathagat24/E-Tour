package com.etour.tour_service_api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourReviewRequest {
    private Long userId;
    private Long tourId;
    private Integer rating;
    private String review;
}
