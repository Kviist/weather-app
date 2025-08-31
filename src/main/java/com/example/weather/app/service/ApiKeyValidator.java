package com.example.weather.app.service;

import com.example.weather.app.config.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Optional;

public class ApiKeyValidator {
    private static final String API_KEY_HEADER = "api-key";
    private static final String API_KEY = "secret-api-key";


    public static Authentication getAuthentication(HttpServletRequest request) {
        Optional<String> requestApiKey = Optional.ofNullable(request.getHeader(API_KEY_HEADER));

        if (requestApiKey.isEmpty()) {
            throw new BadCredentialsException("No API Key provided");
        }

        if (!requestApiKey.get().equals(API_KEY)) {
            throw new BadCredentialsException("API Key is invalid");
        }

        return new ApiKeyAuthentication(requestApiKey.get(), AuthorityUtils.NO_AUTHORITIES);
    }
}
