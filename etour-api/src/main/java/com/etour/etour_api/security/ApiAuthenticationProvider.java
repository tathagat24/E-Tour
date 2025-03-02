package com.etour.etour_api.security;

import com.etour.etour_api.domain.ApiAuthentication;
import com.etour.etour_api.domain.UserPrincipal;
import com.etour.etour_api.dto.User;
import com.etour.etour_api.exception.ApiException;
import com.etour.etour_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.etour.etour_api.domain.ApiAuthentication.authenticated;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiAuthentication apiAuthentication = authenticationFunction.apply(authentication);
        User user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null) {
            String userCredential = userService.getUserCredentialById(user.getId());
            UserPrincipal userPrincipal = new UserPrincipal(user, userCredential);
            validAccount.accept(userPrincipal);
            if (encoder.matches(apiAuthentication.getPassword(), userCredential)) {
                return authenticated(user, userPrincipal.getAuthorities());
            } else {
                throw new BadCredentialsException("Email and/or password incorrect. Please try again");
            }
        } else {
            throw new ApiException("Unable to authenticate");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthentication.class.isAssignableFrom(authentication);
    }

    private final Function<Authentication, ApiAuthentication> authenticationFunction = authentication ->
            (ApiAuthentication) authentication;

    private final Consumer<UserPrincipal> validAccount = userPrincipal -> {
        if (!userPrincipal.isAccountNonLocked()) { throw new LockedException("Your account is currently locked"); }
        if (!userPrincipal.isEnabled()) { throw new DisabledException("Your account is currently disabled"); }
        if (!userPrincipal.isCredentialsNonExpired()) { throw new CredentialsExpiredException("Your password has expired. Please update your password"); }
        if (!userPrincipal.isAccountNonExpired()) { throw new AccountExpiredException("Your account has expired. Please contact administrator"); }
    };
}
