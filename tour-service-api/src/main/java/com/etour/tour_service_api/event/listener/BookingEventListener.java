package com.etour.tour_service_api.event.listener;

import com.etour.tour_service_api.event.BookingEvent;
import com.etour.tour_service_api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 10-02-2025
 */

@Component
@RequiredArgsConstructor
public class BookingEventListener {
    private final EmailService emailService;

    @EventListener
    public void onBookingEvent(BookingEvent bookingEvent) {
        switch (bookingEvent.getEventType()) {
            case BOOKING_CONFIRMED, BOOKING_CANCELLED -> emailService.sendBookingPdfEmail(bookingEvent.getTourBookingDto());
            default -> {}
        }
    }

}
