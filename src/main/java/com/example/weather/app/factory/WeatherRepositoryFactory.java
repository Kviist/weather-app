package com.example.weather.app.factory;

import com.example.weather.app.constants.WeatherParameter;
import com.example.weather.app.client.WeatherApiClient;
import com.example.weather.app.client.WeatherApi;
import org.springframework.web.client.RestTemplate;

public class WeatherRepositoryFactory {
    public static WeatherApi create(RestTemplate restTemplate, String baseUrl, WeatherParameter parameter) {
        return new WeatherApiClient(restTemplate, baseUrl, parameter.number);
    }
}
