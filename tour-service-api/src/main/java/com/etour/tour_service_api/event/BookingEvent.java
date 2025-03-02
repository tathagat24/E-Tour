package com.etour.tour_service_api.event;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 10-02-2025
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {
    private TourBookingDto tourBookingDto;
    private EventType eventType;
}
