package com.example.weather.app.client;

import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Station;
import com.example.weather.app.model.DataPointResponse;
import com.example.weather.app.model.StationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * API Client that is responsible for communicating with the weather api
 * Creates URLs, sends http get requests and parses the response data
 * Each client only reads one of the api parameters
 */
public class WeatherApiClient implements WeatherApi {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final int parameter;
    private final Logger logger = LoggerFactory.getLogger(WeatherApiClient.class);

    public WeatherApiClient(RestTemplate restTemplate, String baseUrl, int parameter) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.parameter = parameter;
    }

    @Override
    public List<Station> getAllStations() {
        String url = String.format("%s/parameter/%s.json", this.baseUrl, this.parameter);
        Optional<StationResponse> response = Optional.ofNullable(restTemplate.getForObject(url, StationResponse.class));

        if (response.isEmpty()) {
            logger.info("Failed to read stations from api using parameter {}", this.parameter);
            return Collections.emptyList();
        }

        logger.info("Received {} stations from api using parameter {}", response.get().station().size(), this.parameter);
        return response.get().station();
    }

    @Override
    public List<DataPoint> getStationDataForPeriod(String stationId, String timePeriod) {
        String url = String.format("%s/parameter/%s/station/%s/period/%s/data.json", this.baseUrl, this.parameter, stationId, timePeriod);
        Optional<DataPointResponse> response = Optional.ofNullable(restTemplate.getForObject(url, DataPointResponse.class));

        if (response.isEmpty()) {
            logger.info("Failed to read data from api using parameter {}", this.parameter);
            return Collections.emptyList();
        }

        logger.info("Received {} data points from api using parameter {}", response.get().value().size(), this.parameter);
        return response.get().value();
    }
}