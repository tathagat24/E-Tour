package com.etour.tour_service_api.resource;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

@RestController
@RequestMapping(path = { "/payment" })
@RequiredArgsConstructor
public class PaymentResource {
    private final PaymentService paymentService;

    @PostMapping("/checkout/hosted")
    public String hostedCheckout(@RequestBody TourBookingDto tourBookingDto) throws StripeException {
        return paymentService.createCheckoutSession(tourBookingDto);
    }


}
