package com.etour.tour_service_api.exception;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

public class ApiException extends RuntimeException {

    public ApiException() {
        super("An error occurred");
    }

    public ApiException(String message) {
        super(message);
    }

}
