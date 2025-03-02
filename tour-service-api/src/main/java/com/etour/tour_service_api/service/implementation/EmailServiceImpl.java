package com.etour.tour_service_api.service.implementation;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.exception.ApiException;
import com.etour.tour_service_api.service.EmailService;
import com.etour.tour_service_api.utils.PdfDocumentUtils;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 10-02-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value(value = "${spring.mail.verify.host}")
    private String host;
    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendBookingPdfEmail(TourBookingDto tourBookingDto) {
        try {
            byte[] pdfBytes = PdfDocumentUtils.generateBookingPdf(tourBookingDto);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(tourBookingDto.getUser().getEmail());
            helper.setFrom(fromEmail);
            helper.setSubject("Booking Confirmation - " + tourBookingDto.getTourName());
            helper.setText("Hello " + tourBookingDto.getUser().getFirstName() + ", \n\nPlease find your booking confirmation for the " + tourBookingDto.getTourName() + " attached.\n\nRegards, \nYour E-Tour Agency");

            ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
            helper.addAttachment("Booking_Confirmation.pdf", dataSource);

            mailSender.send(message);

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to send email");
        }
    }
}
