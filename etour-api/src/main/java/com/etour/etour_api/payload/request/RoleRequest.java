package com.etour.etour_api.payload.request;

import com.etour.etour_api.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRequest {
    @NotNull(message = "Role cannot be empty or null")
    private Role role;
}
