package com.etour.etour_api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@Builder
@Getter
@Setter
public class Token {
    private String access;
    private String refresh;
}
