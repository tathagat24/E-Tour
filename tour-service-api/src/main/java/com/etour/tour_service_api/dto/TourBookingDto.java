package com.etour.tour_service_api.dto;

import com.etour.tour_service_api.enumeration.BookingStatus;
import com.etour.tour_service_api.enumeration.TourCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Data
@Builder
public class TourBookingDto {
    private Long id;
    private String referenceId;
    private String bookingDate;
    private BookingStatus bookingStatus;
    private Double totalPrice;

    private TourCategory categoryName;

    private String subCategoryName;

    private String tourId;
    private String tourName;
    private String description;
    private String duration;
    private String startDate;
    private String endDate;

    private User user;

    private List<PassengerDto> passengers;
}
