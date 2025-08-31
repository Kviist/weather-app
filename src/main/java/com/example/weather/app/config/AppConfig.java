package com.example.weather.app.config;

import com.example.weather.app.constants.WeatherParameter;
import com.example.weather.app.factory.WeatherRepositoryFactory;
import com.example.weather.app.client.WeatherApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Creates two different api clients
 * One to read wind data
 * One to read temperature data
 */
@Configuration
public class AppConfig {
    @Value("${weather.api.url}")
    private String baseUrl;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public WeatherApi windClient(RestTemplate restTemplate) {
        return WeatherRepositoryFactory.create(restTemplate, baseUrl, WeatherParameter.WIND);
    }

    @Bean
    public WeatherApi temperatureClient(RestTemplate restTemplate) {
        return WeatherRepositoryFactory.create(restTemplate, baseUrl, WeatherParameter.TEMPERATURE);
    }
}
