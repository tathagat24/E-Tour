package com.etour.etour_api.service;

import com.etour.etour_api.domain.Token;
import com.etour.etour_api.domain.TokenData;
import com.etour.etour_api.dto.User;
import com.etour.etour_api.enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

public interface JwtService {
    String createToken(User user, Function<Token, String> tokenFunction);
    Optional<String> extractToken(HttpServletRequest request, String cookieName);
    void addCookie(HttpServletResponse response, User user, TokenType tokenType);
    <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);
    void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName);
}
