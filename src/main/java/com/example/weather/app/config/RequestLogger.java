package com.example.weather.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogger {
    @Bean
    public CommonsRequestLoggingFilter logger() {
        return new CommonsRequestLoggingFilter();
    }
}
