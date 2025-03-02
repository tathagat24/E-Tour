package com.etour.tour_service_api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@JsonInclude(value = NON_DEFAULT)
public record Response(
        String time,
        int code,
        String path,
        HttpStatus status,
        String message,
        String exception,
        Map<?, ?> data
) {}
