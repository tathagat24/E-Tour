package com.etour.etour_api.utils;

import com.etour.etour_api.dto.User;
import com.etour.etour_api.entity.UserEntity;
import com.etour.etour_api.enumeration.Role;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

public class UserUtils {

    public static UserEntity createUserEntity(String firstName, String middleName, String lastName, String email, Role role) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .email(email)
                .phone(EMPTY)
                .bio(EMPTY)
                .imageUrl("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                .role(role)
                .lastLogin(now())
                .loginAttempts(0)
                .enabled(false)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    public static User fromUserEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .middleName(userEntity.getMiddleName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .bio(userEntity.getBio())
                .imageUrl(userEntity.getImageUrl())
                .lastLogin(userEntity.getLastLogin().toString())
                .createdAt(userEntity.getCreatedAt().toString())
                .updatedAt(userEntity.getUpdatedAt().toString())
                .role(userEntity.getRole())
                .accountNonExpired(userEntity.isAccountNonExpired())
                .accountNonLocked(userEntity.isAccountNonLocked())
                .enabled(userEntity.isEnabled())
                .addressLine(userEntity.getAddressEntity().getAddressLine())
                .city(userEntity.getAddressEntity().getCity())
                .state(userEntity.getAddressEntity().getState())
                .country(userEntity.getAddressEntity().getCountry())
                .zipCode(userEntity.getAddressEntity().getZipCode())
                .build();
    }
}
