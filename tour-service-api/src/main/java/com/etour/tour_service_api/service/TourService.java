package com.etour.tour_service_api.service;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.entity.DepartureEntity;
import com.etour.tour_service_api.entity.TourEntity;
import com.etour.tour_service_api.entity.TourReviewEntity;
import com.etour.tour_service_api.payload.request.DeparturesRequest;
import com.etour.tour_service_api.payload.request.TourBookingRequest;
import com.etour.tour_service_api.payload.request.TourRequest;
import com.etour.tour_service_api.payload.request.TourReviewRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

public interface TourService {
    TourEntity createTour(TourRequest tourRequest);
    TourEntity uploadTourImage(Long tourId, MultipartFile imageFile);
    List<TourEntity> getAllTours();
    List<TourEntity> getAllToursByTourSubcategoryId(Long tourSubcategoryId);
    TourReviewEntity createTourReview(TourReviewRequest tourReviewRequest);
    TourBookingDto createTourBooking(TourBookingRequest tourBookingRequest);
    TourBookingDto getTourBookingById(Long bookingId);
    List<TourBookingDto> getTourBookingsByUserId(Long userId);
    List<DepartureEntity> addTourDepartures(DeparturesRequest departuresRequest);
    List<TourEntity> getAllPopularsTours();
    TourEntity togglePopularTours(Long tourId);
    TourBookingDto updateTourBookingSuccess(String bookingReference);
}
