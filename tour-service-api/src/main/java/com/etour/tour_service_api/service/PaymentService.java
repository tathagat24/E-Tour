package com.etour.tour_service_api.service;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.stripe.exception.StripeException;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

public interface PaymentService {
    String createCheckoutSession(TourBookingDto tourBookingDto) throws StripeException;
}
