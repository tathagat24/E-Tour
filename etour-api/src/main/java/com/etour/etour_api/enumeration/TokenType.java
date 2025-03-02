package com.etour.etour_api.enumeration;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@SuppressWarnings("LombokGetterMayBeUsed")
public enum TokenType {
    ACCESS("access-token"),
    REFRESH("refresh-token");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
