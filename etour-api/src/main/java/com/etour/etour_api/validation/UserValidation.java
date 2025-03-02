package com.etour.etour_api.validation;

import com.etour.etour_api.entity.UserEntity;
import com.etour.etour_api.exception.ApiException;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

public class UserValidation {

    public static void verifyAccountStatus(UserEntity userEntity) {
        if (!userEntity.isEnabled()) { throw new ApiException("Account is disabled"); }
        if (!userEntity.isAccountNonExpired()) { throw new ApiException("Account is expired"); }
        if (!userEntity.isAccountNonLocked()) { throw new ApiException("Account is locked"); }
    }

}
