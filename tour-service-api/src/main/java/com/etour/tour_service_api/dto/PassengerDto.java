package com.etour.tour_service_api.dto;

import com.etour.tour_service_api.enumeration.Gender;
import com.etour.tour_service_api.enumeration.PassengerType;
import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Data
@Builder
public class PassengerDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private Integer age;
    private Gender gender;
    private PassengerType passengerType;
    private Double passengerCost;
}
