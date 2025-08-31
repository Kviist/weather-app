package com.example.weather.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class WeatherApplication {
    Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @EventListener
    public void onApplicationStarted(ContextRefreshedEvent event) {
        logger.info("Welcome to the Weather Application");
        logger.info("Available endpoint: /api/weather/stations");
        logger.info("Available endpoint: /api/weather/station/{id}/latest-hour");
        logger.info("Available endpoint: /api/weather/station/{id}/latest-day");
        logger.info("Available endpoint: /api/weather/station/{id}/latest-months");
        logger.info("Available endpoint: /api/weather/station/{id}/filter?from=yyyy-MM-dd&to=yyyy-MM-dd");
    }
}
