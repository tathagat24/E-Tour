package com.etour.etour_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.etour.etour_api.constant.ApiConstant.STRENGTH;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }
}
