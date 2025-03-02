package com.etour.etour_api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@Getter
@Setter
public abstract class JwtConfiguration {
    @Value(value = "${jwt.expiration}")
    private Long expiration;
    @Value(value = "${jwt.secret}")
    private String secret;
}
