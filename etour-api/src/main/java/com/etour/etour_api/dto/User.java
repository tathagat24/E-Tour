package com.etour.etour_api.dto;

import com.etour.etour_api.enumeration.Role;
import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
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
    private String lastLogin;
    private String createdAt;
    private String updatedAt;
    private Role role;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
