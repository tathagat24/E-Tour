package com.etour.etour_api.service;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

public interface EmailService {
    void sendNewAccountEmail(String name, String email, String key);
    void sendPasswordResetEmail(String name, String email, String key);
}
