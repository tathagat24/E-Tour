package com.etour.tour_service_api.service;

import com.etour.tour_service_api.dto.TourBookingDto;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 10-02-2025
 */

public interface EmailService {
    void sendBookingPdfEmail(TourBookingDto tourBookingDto);
}
