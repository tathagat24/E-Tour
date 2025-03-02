package com.etour.tour_service_api.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Data
@Builder
public class User {
    private Long id;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String bio;
    private String imageUrl;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}

