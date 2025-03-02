package com.etour.etour_api.resource;

import com.etour.etour_api.dto.User;
import com.etour.etour_api.handler.ApiLogoutHandler;
import com.etour.etour_api.payload.request.*;
import com.etour.etour_api.payload.response.Response;
import com.etour.etour_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.etour.etour_api.constant.ApiConstant.USER_IMAGE_FILE_STORAGE;
import static com.etour.etour_api.utils.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@RestController
@RequestMapping(path = { "/user" })
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final ApiLogoutHandler logoutHandler;

    @PostMapping(path = "/register")
    public ResponseEntity<Response> createUser(@RequestBody @Valid UserRequest userRequest, HttpServletRequest request) {
        userService.createUser(userRequest.getFirstName(), userRequest.getMiddleName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.created(getUri()).body(getResponse(request, emptyMap(), "Account created. Check your email to enable your account", CREATED));
    }

    @GetMapping(path = "/verify/account")
    public ResponseEntity<Response> verifyAccount(@RequestParam(value = "key") String key, HttpServletRequest request) {
        userService.verifyAccountKey(key);
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account verified.", OK));
    }

    // START - User Profile Feature

    @GetMapping(path = "/profile")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Response> profile(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        User user = userService.getUserByUserId(userPrincipal.getUserId());
        return ResponseEntity.ok().body(getResponse(request, of("user", user), "Profile retrieved", OK));
    }

    @PatchMapping(path = "/update")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Response> update(@AuthenticationPrincipal User userPrincipal, @RequestBody UserRequest userRequest, HttpServletRequest request) {
        User user = userService.updateUser(
                userPrincipal.getUserId(),
                userRequest.getFirstName(),
                userRequest.getMiddleName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPhone(),
                userRequest.getBio(),
                userRequest.getAddressLine(),
                userRequest.getCity(),
                userRequest.getState(),
                userRequest.getCountry(),
                userRequest.getZipCode()
        );
        return ResponseEntity.ok().body(getResponse(request, of("user", user), "User updated successfully", OK));
    }

    @PatchMapping(path = "/updaterole")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Response> updateRole(@AuthenticationPrincipal User userPrincipal, @RequestBody @Valid RoleRequest roleRequest, HttpServletRequest request) {
        userService.updateRole(userPrincipal.getUserId(), roleRequest.getRole());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Role updated successfully", OK));
    }

    @PatchMapping(path = "/toggleaccountexpired")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Response> toggleAccountExpired(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        userService.toggleAccountExpired(userPrincipal.getUserId());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account updated successfully", OK));
    }

    @PatchMapping(path = "/toggleaccountlocked")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Response> toggleAccountLocked(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        userService.toggleAccountLocked(userPrincipal.getUserId());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account updated successfully", OK));
    }

    @PatchMapping(path = "/toggleaccountenabled")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Response> toggleAccountEnabled(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        userService.toggleAccountEnabled(userPrincipal.getUserId());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account updated successfully", OK));
    }

    @PatchMapping(path = "/updatepassword")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Response> updatePassword(@AuthenticationPrincipal User userPrincipal, @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request) {
        userService.updatePassword(userPrincipal.getUserId(), updatePasswordRequest.getPassword(), updatePasswordRequest.getNewPassword(), updatePasswordRequest.getConfirmNewPassword());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Password updated successfully", OK));
    }

    @PatchMapping(path = "/photo")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Response> uploadPhoto(@AuthenticationPrincipal User userPrincipal, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        String imageUrl = userService.uploadPhoto(userPrincipal.getUserId(), file);
        return ResponseEntity.ok().body(getResponse(request, of("imageUrl", imageUrl), "Photo updated successfully", OK));
    }

    @GetMapping(path = "/image/{fileName}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable(value = "fileName") String fileName ) throws IOException {
        return Files.readAllBytes(Paths.get(USER_IMAGE_FILE_STORAGE + fileName));
    }

    // END - User Profile Feature

    // START - Reset Password when user is not logged in

    @PostMapping(path = "/resetpassword")
    public ResponseEntity<Response> resetPassword(@RequestBody @Valid EmailRequest emailRequest, HttpServletRequest request) {
        userService.resetPassword(emailRequest.getEmail());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "We sent you an email to reset your password", OK));
    }

    @GetMapping(path = "/verify/password")
    public ResponseEntity<Response> verifyPassword(@RequestParam(value = "key") String key, HttpServletRequest request) {
        User user = userService.verifyPasswordKey(key);
        return ResponseEntity.ok().body(getResponse(request, of("user", user), "Enter new password.", OK));
    }

    @PostMapping(path = "/resetpassword/reset")
    public ResponseEntity<Response> doResetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        userService.updatePassword(resetPasswordRequest.getUserId(), resetPasswordRequest.getNewPassword(), resetPasswordRequest.getConfirmNewPassword());
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Password reset successfully.", OK));
    }

    // END - Reset Password when user is not logged in

    @GetMapping(path = "/list")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Response> toggleCredentialsExpired(HttpServletRequest request) {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(getResponse(request, of("users", users), "Users retrieved", OK));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "You've logged out successfully", OK));
    }

    private URI getUri() {
        return URI.create("");
    }
}
