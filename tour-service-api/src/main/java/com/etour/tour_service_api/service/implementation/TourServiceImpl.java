package com.etour.tour_service_api.service.implementation;

import com.etour.tour_service_api.dto.TourBookingDto;
import com.etour.tour_service_api.entity.*;
import com.etour.tour_service_api.enumeration.BookingStatus;
import com.etour.tour_service_api.enumeration.EventType;
import com.etour.tour_service_api.event.BookingEvent;
import com.etour.tour_service_api.exception.ApiException;
import com.etour.tour_service_api.payload.request.*;
import com.etour.tour_service_api.repository.*;
import com.etour.tour_service_api.service.TourService;
import com.etour.tour_service_api.utils.TourUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_IMAGE_FILE_STORAGE;
import static com.etour.tour_service_api.utils.TourUtils.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 30-01-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ApplicationEventPublisher publisher;
    private final PassengerRepository passengerRepository;
    private final TourPriceRepository tourPriceRepository;
    private final ItineraryRepository itineraryRepository;
    private final DepartureRepository departureRepository;
    private final TourReviewRepository tourReviewRepository;
    private final TourSubCategoryRepository tourSubCategoryRepository;

    @Override
    public TourEntity createTour(TourRequest tourRequest) {
        TourSubcategoryEntity tourSubcategoryEntity = getTourSubcategoryEntityById(tourRequest.getTourSubcategoryId());
        TourEntity tourEntity = createTourEntity(tourRequest);
        tourEntity.setTourSubcategoryEntity(tourSubcategoryEntity);
        TourEntity savedTourEntity = tourRepository.save(tourEntity);
        saveTourItineraries(savedTourEntity, tourRequest.getItineraryRequests());
        saveTourPriceEntity(savedTourEntity, tourRequest.getTourPriceRequest());
        return savedTourEntity;
    }

    @Override
    public TourEntity uploadTourImage(Long tourId, MultipartFile imageFile) {
        TourEntity tourEntity = getTourEntityById(tourId);
        tourEntity.setImageUrl(photoFunction.apply(tourEntity.getTourId(), imageFile));
        return tourRepository.save(tourEntity);
    }

    @Override
    public List<TourEntity> getAllTours() {
        return tourRepository.findAll();
    }

    @Override
    public List<TourEntity> getAllPopularsTours() {
        return tourRepository.findAllByIsPopularTrue();
    }

    @Override
    public TourEntity togglePopularTours(Long tourId) {
        TourEntity tourEntity = getTourEntityById(tourId);
        tourEntity.setPopular(!tourEntity.isPopular());
        return tourRepository.save(tourEntity);
    }

    @Override
    public TourBookingDto updateTourBookingSuccess(String bookingReference) {
        BookingEntity bookingEntity = getBookingEntityByBookingReference(bookingReference);
        bookingEntity.setBookingStatus(BookingStatus.CONFIRMED);
        BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);
        List<PassengerEntity> passengerEntities = getPassengerEntitiesByBookingEntity(bookingEntity);
        TourBookingDto tourBookingDto = toTourBookingDto(
                savedBookingEntity,
                savedBookingEntity.getTourEntity().getTourSubcategoryEntity().getTourCategoryEntity(),
                savedBookingEntity.getTourEntity().getTourSubcategoryEntity(),
                savedBookingEntity.getTourEntity(),
                savedBookingEntity.getUserEntity(),
                passengerEntities
        );
        publisher.publishEvent(new BookingEvent(tourBookingDto, EventType.BOOKING_CONFIRMED));
        return tourBookingDto;
    }

    private BookingEntity getBookingEntityByBookingReference(String bookingReference) {
        return bookingRepository.findBookingEntityByReferenceId(bookingReference).orElseThrow(() -> new ApiException("Booking not found"));
    }

    @Override
    public List<TourEntity> getAllToursByTourSubcategoryId(Long tourSubcategoryId) {
        return tourRepository.findAllByTourSubcategoryEntity(getTourSubcategoryEntityById(tourSubcategoryId));
    }

    @Override
    public TourReviewEntity createTourReview(TourReviewRequest tourReviewRequest) {
        TourEntity tourEntity = getTourEntityById(tourReviewRequest.getTourId());
        UserEntity userEntity = getUserEntityById(tourReviewRequest.getUserId());
        TourReviewEntity tourReviewEntity = new TourReviewEntity();
        tourReviewEntity.setRating(tourReviewRequest.getRating());
        tourReviewEntity.setReview(tourReviewRequest.getReview());
        tourReviewEntity.setUserEntity(userEntity);
        tourReviewEntity.setTourEntity(tourEntity);
        return tourReviewRepository.save(tourReviewEntity);
    }

    @Override
    public TourBookingDto createTourBooking(TourBookingRequest tourBookingRequest) {
        BookingEntity savedBookingEntity = saveBookingEntity(tourBookingRequest);
        List<PassengerEntity> savedPassengerEntities = savePassengerEntities(tourBookingRequest.getPassengers(), savedBookingEntity);
        return toTourBookingDto(
                savedBookingEntity,
                savedBookingEntity.getTourEntity().getTourSubcategoryEntity().getTourCategoryEntity(),
                savedBookingEntity.getTourEntity().getTourSubcategoryEntity(),
                savedBookingEntity.getTourEntity(),
                savedBookingEntity.getUserEntity(),
                savedPassengerEntities
        );
    }

    @Override
    public TourBookingDto getTourBookingById(Long bookingId) {
        BookingEntity bookingEntity = getBookingEntityById(bookingId);
        List<PassengerEntity> passengerEntities = getPassengerEntitiesByBookingEntity(bookingEntity);
        return toTourBookingDto(
                bookingEntity,
                bookingEntity.getTourEntity().getTourSubcategoryEntity().getTourCategoryEntity(),
                bookingEntity.getTourEntity().getTourSubcategoryEntity(),
                bookingEntity.getTourEntity(),
                bookingEntity.getUserEntity(),
                passengerEntities
        );
    }

    @Override
    public List<TourBookingDto> getTourBookingsByUserId(Long userId) {
        UserEntity userEntity = getUserEntityById(userId);
        List<BookingEntity> bookingEntities = bookingRepository.findAllByUserEntity(userEntity);
        return bookingEntities.stream().map(TourUtils::toTourBookingDto).toList();
    }

    @Override
    public List<DepartureEntity> addTourDepartures(DeparturesRequest departuresRequest) {
        TourEntity tourEntity = getTourEntityById(departuresRequest.getTourId());
        return saveDepartureEntities(departuresRequest.getDepartures(), tourEntity);
    }

    private List<DepartureEntity> saveDepartureEntities(List<DepartureRequest> departures, TourEntity tourEntity) {
        for (DepartureRequest departureRequest : departures) {
            DepartureEntity departureEntity = createDepartureEntity(departureRequest);
            departureEntity.setTourEntity(tourEntity);
            departureRepository.save(departureEntity);
        }
        return departureRepository.findAllByTourEntity(tourEntity);
    }

    private DepartureEntity createDepartureEntity(DepartureRequest departureRequest) {
        return DepartureEntity.builder()
                .startDate(LocalDate.parse(departureRequest.getStartDate()))
                .endDate(LocalDate.parse(departureRequest.getEndDate()))
                .build();
    }

    private List<PassengerEntity> getPassengerEntitiesByBookingEntity(BookingEntity bookingEntity) {
        return passengerRepository.findAllByBookingEntity(bookingEntity);
    }

    private BookingEntity getBookingEntityById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new ApiException("Booking not found"));
    }

    private final BiFunction<String, MultipartFile, String> photoFunction = (tourId, file) -> {
        String fileName = tourId + ".png";
        try {
            Path fileStorageLocation = Paths.get(TOUR_IMAGE_FILE_STORAGE).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            // /tour-category/image/964d4475-ebd7-1a06-9842-37143e2c2ceb.png?timestamp=1738144361295
            return "http://localhost:8085/api/v1/tour-service/tour/image/" + fileName;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image");
        }
    };

    private void saveTourPriceEntity(TourEntity savedTourEntity, TourPriceRequest tourPriceRequest) {
        tourPriceRepository.save(createTourPriceEntity(savedTourEntity, tourPriceRequest));
    }

    private BookingEntity saveBookingEntity(TourBookingRequest tourBookingRequest) {
        TourEntity tourEntity = getTourEntityById(tourBookingRequest.getTourId());
        UserEntity userEntity = getUserEntityById(tourBookingRequest.getUserId());
        DepartureEntity departureEntity = getDepartureEntityById(tourBookingRequest.getDepartureId());
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingDate(LocalDateTime.now());
        bookingEntity.setBookingStatus(BookingStatus.PENDING);
        bookingEntity.setTotalPrice(tourBookingRequest.getTotalPrice());
        bookingEntity.setTourEntity(tourEntity);
        bookingEntity.setUserEntity(userEntity);
        bookingEntity.setDepartureEntity(departureEntity);
        return bookingRepository.save(bookingEntity);
    }

    private DepartureEntity getDepartureEntityById(Long departureId) {
        return departureRepository.findById(departureId).orElseThrow(() -> new ApiException("Departure not found"));
    }

    private List<PassengerEntity> savePassengerEntities(List<PassengerRequest> passengers, BookingEntity savedBookingEntity) {
        for (PassengerRequest passengerRequest : passengers) {
            PassengerEntity passengerEntity = createPassengerEntity(passengerRequest);
            passengerEntity.setBookingEntity(savedBookingEntity);
            passengerEntity.setTourEntity(savedBookingEntity.getTourEntity());
            passengerRepository.save(passengerEntity);
        }
        return passengerRepository.findAllByBookingEntity(savedBookingEntity);
    }

    private void saveTourItineraries(TourEntity savedTourEntity, List<ItineraryRequest> itineraryRequests) {
        for (ItineraryRequest itineraryRequest : itineraryRequests) {
            itineraryRepository.save(createItineraryEntity(itineraryRequest, savedTourEntity));
        }
    }

    private TourEntity getTourEntityById(Long tourId) {
        return tourRepository.findById(tourId).orElseThrow(() -> new ApiException("Tour not found"));
    }

    private TourSubcategoryEntity getTourSubcategoryEntityById(Long tourSubcategoryId) {
        return tourSubCategoryRepository.findById(tourSubcategoryId).orElseThrow(() -> new ApiException("Tour SubCategory not found"));
    }

    private UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException("user not found"));
    }
}
