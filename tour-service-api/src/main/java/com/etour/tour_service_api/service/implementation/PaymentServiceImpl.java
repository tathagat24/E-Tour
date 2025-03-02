package com.etour.tour_service_api.service.implementation;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 09-02-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class PaymentServiceImpl implements PaymentService {

    @Value(value = "${stripe.secret}")
    private String STRIPE_API_KEY;

    @Override
    public String createCheckoutSession(TourBookingDto tourBookingDto) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        String clientBaseUrl = "http://localhost:5173";

        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(clientBaseUrl + "/payment/success/" + tourBookingDto.getReferenceId())
                        .setCancelUrl(clientBaseUrl + "/payment/cancel/" + tourBookingDto.getReferenceId());
        paramsBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .putMetadata("booking_id", tourBookingDto.getReferenceId())
                                                        .setName(tourBookingDto.getTourName())
                                                        .build()
                                        )
                                        .setCurrency("INR")
                                        .setUnitAmountDecimal(BigDecimal.valueOf(tourBookingDto.getTotalPrice() * 100))
                                        .build()
                        )
                        .build()
        );

        Session session = Session.create(paramsBuilder.build());

        return session.getUrl();
    }
}
