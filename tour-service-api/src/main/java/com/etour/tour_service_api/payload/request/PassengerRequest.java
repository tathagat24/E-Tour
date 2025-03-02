package com.etour.tour_service_api.payload.request;

import com.etour.tour_service_api.enumeration.Gender;
import com.etour.tour_service_api.enumeration.PassengerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 05-02-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerRequest {
    @NotEmpty(message = "First name cannot be empty or null")
    private String firstName;
    @NotEmpty(message = "Middle name cannot be empty or null")
    private String middleName;
    @NotEmpty(message = "Last name cannot be empty or null")
    private String lastName;
    private String email;
    private String phone;
    @NotEmpty(message = "Date of birth cannot be empty or null")
    private String dateOfBirth;
    @NotNull(message = "Age cannot be empty or null")
    private Integer age;
    @NotNull(message = "Gender cannot be empty or null")
    private Gender gender;
    @NotNull(message = "Passenger Type cannot be empty or null")
    private PassengerType passengerType;
    @NotNull(message = "Passenger Cost cannot be empty or null")
    private Double passengerCost;
}
