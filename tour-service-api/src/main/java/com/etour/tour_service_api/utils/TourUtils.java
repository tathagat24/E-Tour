package com.etour.tour_service_api.utils;

import com.etour.tour_service_api.dto.*;
import com.etour.tour_service_api.entity.*;
import com.etour.tour_service_api.payload.request.ItineraryRequest;
import com.etour.tour_service_api.payload.request.PassengerRequest;
import com.etour.tour_service_api.payload.request.TourPriceRequest;
import com.etour.tour_service_api.payload.request.TourRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

public class TourUtils {

    public static TourCategoryDto fromTourCategoryEntity(TourCategoryEntity tourCategoryEntity) {
        return TourCategoryDto.builder()
                .id(tourCategoryEntity.getId())
                .referenceId(tourCategoryEntity.getReferenceId())
                .categoryName(tourCategoryEntity.getCategoryName())
                .imageUrl(tourCategoryEntity.getImageUrl())
                .createdAt(tourCategoryEntity.getCreatedAt().toString())
                .updatedAt(tourCategoryEntity.getUpdatedAt().toString())
                .build();
    }

    public static TourSubcategoryDto fromTourSubcategoryEntity(TourSubcategoryEntity tourSubcategoryEntity) {
        return TourSubcategoryDto.builder()
                .id(tourSubcategoryEntity.getId())
                .referenceId(tourSubcategoryEntity.getReferenceId())
                .subCategoryName(tourSubcategoryEntity.getSubCategoryName())
                .imageUrl(tourSubcategoryEntity.getImageUrl())
                .createdAt(tourSubcategoryEntity.getCreatedAt().toString())
                .updatedAt(tourSubcategoryEntity.getUpdatedAt().toString())
                .tourCategoryId(tourSubcategoryEntity.getTourCategoryEntity().getId())
                .build();
    }

    public static TourPriceEntity createTourPriceEntity(TourEntity savedTourEntity, TourPriceRequest tourPriceRequest) {
        return TourPriceEntity.builder()
                .singlePersonPrice(tourPriceRequest.getSinglePersonPrice())
                .extraPersonPrice(tourPriceRequest.getExtraPersonPrice())
                .twinSharingPrice(tourPriceRequest.getTwinSharingPrice())
                .childWithBedPrice(tourPriceRequest.getChildWithBedPrice())
                .childWithoutBedPrice(tourPriceRequest.getChildWithoutBedPrice())
                .tourEntity(savedTourEntity)
                .build();
    }

    public static ItineraryEntity createItineraryEntity(ItineraryRequest itineraryRequest, TourEntity savedTourEntity) {
        return ItineraryEntity.builder()
                .day(itineraryRequest.getDay())
                .itineraryName(itineraryRequest.getItineraryName())
                .description(itineraryRequest.getDescription())
                .tourEntity(savedTourEntity)
                .build();
    }

    public static TourEntity createTourEntity(TourRequest tourRequest) {
        return TourEntity.builder()
                .tourId(UUID.randomUUID().toString())
                .tourName(tourRequest.getTourName())
                .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiekWCHIAty07z8GECv2GJAbdmv1p998meTA&s")
                .description(tourRequest.getDescription())
                .duration(tourRequest.getDuration())
                .startDate(LocalDate.parse(tourRequest.getStartDate()))
                .endDate(LocalDate.parse(tourRequest.getEndDate()))
                .build();
    }

    public static PassengerEntity createPassengerEntity(PassengerRequest passengerRequest) {
        return PassengerEntity.builder()
                .firstName(passengerRequest.getFirstName())
                .middleName(passengerRequest.getMiddleName())
                .lastName(passengerRequest.getLastName())
                .email(passengerRequest.getEmail().isBlank() ? null : passengerRequest.getEmail())
                .phone(passengerRequest.getPhone())
                .dateOfBirth(LocalDate.parse(passengerRequest.getDateOfBirth()))
                .age(passengerRequest.getAge())
                .gender(passengerRequest.getGender())
                .passengerType(passengerRequest.getPassengerType())
                .passengerCost(passengerRequest.getPassengerCost())
                .build();
    }

    public static TourBookingDto toTourBookingDto(BookingEntity savedBookingEntity, TourCategoryEntity tourCategoryEntity, TourSubcategoryEntity tourSubcategoryEntity, TourEntity tourEntity, UserEntity userEntity, List<PassengerEntity> savedPassengerEntities) {

        return TourBookingDto.builder()
                .id(savedBookingEntity.getId())
                .referenceId(savedBookingEntity.getReferenceId())
                .bookingDate(savedBookingEntity.getBookingDate().toString())
                .bookingStatus(savedBookingEntity.getBookingStatus())
                .totalPrice(savedBookingEntity.getTotalPrice())
                .categoryName(tourCategoryEntity.getCategoryName())
                .subCategoryName(tourSubcategoryEntity.getSubCategoryName())
                .tourId(tourEntity.getTourId())
                .tourName(tourEntity.getTourName())
                .description(tourEntity.getDescription())
                .duration(tourEntity.getDuration())
                .startDate(savedBookingEntity.getDepartureEntity().getStartDate().toString())
                .endDate(savedBookingEntity.getDepartureEntity().getEndDate().toString())
                .user(fromUserEntity(userEntity))
                .passengers(getPassengerDtoList(savedPassengerEntities))
                .build();

    }

    public static TourBookingDto toTourBookingDto(BookingEntity savedBookingEntity) {

        return TourBookingDto.builder()
                .id(savedBookingEntity.getId())
                .referenceId(savedBookingEntity.getReferenceId())
                .bookingDate(savedBookingEntity.getBookingDate().toString())
                .bookingStatus(savedBookingEntity.getBookingStatus())
                .totalPrice(savedBookingEntity.getTotalPrice())
                .build();

    }

    private static User fromUserEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .middleName(userEntity.getMiddleName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .bio(userEntity.getBio())
                .imageUrl(userEntity.getImageUrl())
                .addressLine(userEntity.getAddressEntity().getAddressLine())
                .city(userEntity.getAddressEntity().getCity())
                .state(userEntity.getAddressEntity().getState())
                .country(userEntity.getAddressEntity().getCountry())
                .zipCode(userEntity.getAddressEntity().getZipCode())
                .build();
    }

    public static List<PassengerDto> getPassengerDtoList(List<PassengerEntity> savedPassengerEntities) {
        List<PassengerDto> passengerDtoList = new ArrayList<>();
        for (PassengerEntity passengerEntity : savedPassengerEntities) {
            passengerDtoList.add(toPassengerDto(passengerEntity));
        }
        return  passengerDtoList;
    }

    public static PassengerDto toPassengerDto(PassengerEntity passengerEntity) {
        return PassengerDto.builder()
                .firstName(passengerEntity.getFirstName())
                .middleName(passengerEntity.getMiddleName())
                .lastName(passengerEntity.getLastName())
                .email(passengerEntity.getEmail())
                .phone(passengerEntity.getPhone())
                .dateOfBirth(passengerEntity.getDateOfBirth().toString())
                .age(passengerEntity.getAge())
                .gender(passengerEntity.getGender())
                .passengerType(passengerEntity.getPassengerType())
                .passengerCost(passengerEntity.getPassengerCost())
                .build();
    }

}
