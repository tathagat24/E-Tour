package com.etour.etour_api.exception;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

public class ApiException extends RuntimeException {

    public ApiException() {
        super("An error occurred");
    }

    public ApiException(String message) {
        super(message);
    }
}
