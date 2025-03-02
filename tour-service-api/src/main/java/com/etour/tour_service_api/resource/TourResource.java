package com.etour.tour_service_api.resource;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.entity.DepartureEntity;
import com.etour.tour_service_api.entity.TourEntity;
import com.etour.tour_service_api.entity.TourReviewEntity;
import com.etour.tour_service_api.payload.request.DeparturesRequest;
import com.etour.tour_service_api.payload.request.TourBookingRequest;
import com.etour.tour_service_api.payload.request.TourRequest;
import com.etour.tour_service_api.payload.request.TourReviewRequest;
import com.etour.tour_service_api.payload.response.Response;
import com.etour.tour_service_api.service.TourService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_IMAGE_FILE_STORAGE;
import static com.etour.tour_service_api.utils.RequestUtils.getResponse;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 30-01-2025
 */

@RestController
@RequestMapping(path = { "/tour" })
@RequiredArgsConstructor
public class TourResource {
    private final TourService tourService;

    @GetMapping("/list")
    public ResponseEntity<Response> getAllTours(HttpServletRequest request) {
        List<TourEntity> tourEntities = tourService.getAllTours();
        return ResponseEntity.ok().body(getResponse(request, of("tours", tourEntities), null, OK));
    }

    @GetMapping("/popular/list")
    public ResponseEntity<Response> getAllPopularsTours(HttpServletRequest request) {
        List<TourEntity> tourEntities = tourService.getAllPopularsTours();
        return ResponseEntity.ok().body(getResponse(request, of("tours", tourEntities), "Popular Tours retrieved", OK));
    }

    @PatchMapping("/popular/{tourId}")
    public ResponseEntity<Response> togglePopularTours(@PathVariable(value = "tourId") Long tourId, HttpServletRequest request) {
        TourEntity tourEntity = tourService.togglePopularTours(tourId);
        return ResponseEntity.ok().body(getResponse(request, of("tour", tourEntity), "Popular Tour updated successfully", OK));
    }

    @GetMapping
    public ResponseEntity<Response> getAllToursByTourSubcategoryId(@RequestParam(value = "tourSubcategoryId") Long tourSubcategoryId, HttpServletRequest request) {
        List<TourEntity> tourEntities = tourService.getAllToursByTourSubcategoryId(tourSubcategoryId);
        return ResponseEntity.ok().body(getResponse(request, of("tours", tourEntities), "Tours retrieved", OK));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createTour(@RequestBody @Valid TourRequest tourRequest, HttpServletRequest request) {
        TourEntity tourEntity = tourService.createTour(tourRequest);
        return ResponseEntity.created(getUri()).body(getResponse(request, of("tour", tourEntity), "Tour created successfully", CREATED));
    }

    @PostMapping("/departures")
    public ResponseEntity<Response> addTourDepartures(@RequestBody @Valid DeparturesRequest departuresRequest, HttpServletRequest request) {
        List<DepartureEntity> departureEntities = tourService.addTourDepartures(departuresRequest);
        return ResponseEntity.created(getUri()).body(getResponse(request, of("departures", departureEntities), "Departures created successfully", CREATED));
    }

    @PatchMapping("/upload-image")
    public ResponseEntity<Response> uploadTourImage(
            @RequestParam(value = "tourId") Long tourId,
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            HttpServletRequest request
    ) {
        TourEntity tourEntity = tourService.uploadTourImage(tourId, imageFile);
        return ResponseEntity.ok().body(getResponse(request, of("tour", tourEntity), "Tour image uploaded successfully", OK));
    }

    @PostMapping("/review/create")
    public ResponseEntity<Response> createTourReview(@RequestBody @Valid TourReviewRequest tourReviewRequest, HttpServletRequest request) {
        TourReviewEntity tourReviewEntity = tourService.createTourReview(tourReviewRequest);
        return ResponseEntity.created(getUri()).body(getResponse(request, of("tourReview", tourReviewEntity), "Tour review added successfully", CREATED));
    }

    @PostMapping("/booking/create")
    public ResponseEntity<Response> createTourBooking(@RequestBody @Valid TourBookingRequest tourBookingRequest, HttpServletRequest request) {
        TourBookingDto tourBookingDto = tourService.createTourBooking(tourBookingRequest);
        return ResponseEntity.created(getUri()).body(getResponse(request, of("tourBooking", tourBookingDto), "Tour booking created successfully", CREATED));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Response> getTourBookingById(@PathVariable(value = "bookingId") Long bookingId, HttpServletRequest request) {
        TourBookingDto tourBookingDto = tourService.getTourBookingById(bookingId);
        return ResponseEntity.ok().body(getResponse(request, of("tourBooking", tourBookingDto), "Tour booking retrieved", OK));
    }

    @GetMapping("/user/bookings/{userId}")
    public ResponseEntity<Response> getTourBookingsByUserId(@PathVariable(value = "userId") Long userId, HttpServletRequest request) {
        List<TourBookingDto> tourBookingDtoList = tourService.getTourBookingsByUserId(userId);
        return ResponseEntity.ok().body(getResponse(request, of("tourBookings", tourBookingDtoList), "Tour bookings retrieved", OK));
    }

    @PatchMapping("/booking/success/{bookingReference}")
    public  ResponseEntity<Response> updateTourBookingSuccess(@PathVariable(value = "bookingReference") String bookingReference, HttpServletRequest request) {
        TourBookingDto tourBookingDto = tourService.updateTourBookingSuccess(bookingReference);
        return ResponseEntity.ok().body(getResponse(request, of("tourBooking", tourBookingDto), "Booking successfully done.", OK));
    }

    @GetMapping(path = "/image/{fileName}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable(value = "fileName") String fileName ) throws IOException {
        return Files.readAllBytes(Paths.get(TOUR_IMAGE_FILE_STORAGE + fileName));
    }

    private URI getUri() {
        return URI.create("");
    }

}
