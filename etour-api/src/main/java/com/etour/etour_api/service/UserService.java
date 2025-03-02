package com.etour.etour_api.service;

import com.etour.etour_api.dto.User;
import com.etour.etour_api.enumeration.LoginType;
import com.etour.etour_api.enumeration.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

public interface UserService {
    void createUser(String firstName, String middleName, String lastName, String email, String password);
    void verifyAccountKey(String key);
    void updateLoginAttempt(String email, LoginType loginType);
    User getUserByUserId(String userId);
    User getUserByEmail(String email);
    String getUserCredentialById(Long id);
    void resetPassword(String email);
    User verifyPasswordKey(String key);
    void updatePassword(String userId, String newPassword, String confirmNewPassword);
    User updateUser(String userId, String firstName, String middleName, String lastName, String email, String phone, String bio, String addressLine, String city, String state, String country, String zipCode);
    void updateRole(String userId, Role role);
    void toggleAccountExpired(String userId);
    void toggleAccountLocked(String userId);
    void toggleAccountEnabled(String userId);
    void updatePassword(String userId, String password, String newPassword, String confirmNewPassword);
    String uploadPhoto(String userId, MultipartFile file);
    List<User> getUsers();
    User getUserById(Long id);
}
