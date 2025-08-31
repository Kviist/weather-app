package com.example.weather.app.service;

import com.example.weather.app.client.WeatherApi;
import com.example.weather.app.constants.WeatherTimePeriod;
import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Station;
import com.example.weather.app.dto.Weather;
import com.example.weather.app.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherServiceImplementationTest {
    @MockitoBean(name = "windClient")
    private WeatherApi windClient;

    @MockitoBean(name = "temperatureClient")
    private WeatherApi temperatureClient;

    @Autowired
    private WeatherServiceImplementation weatherService;

    private final TestHelper testHelper = new TestHelper();

    @Test
    void getAllStationsShouldUseWindClient() {
        List<Station> mockStations = testHelper.mockStations(3);
        when(windClient.getAllStations()).thenReturn(mockStations);

        List<Station> actual = weatherService.getAllStations();

        assertEquals(mockStations, actual);
        verify(temperatureClient, times(0)).getAllStations();
    }

    @Test
    void shouldReturnSameAmountOfWeathersAsDataPoints() {
        when(windClient.getStationDataForPeriod(anyString(), anyString())).thenReturn(testHelper.mockDataPoints(3));
        when(temperatureClient.getStationDataForPeriod(anyString(), anyString())).thenReturn(testHelper.mockDataPoints(3));

        List<Weather> actual = weatherService.getStationDataForPeriod("123", WeatherTimePeriod.LATEST_MONTHS);

        assertEquals(3, actual.size());
    }

    @Test
    void shouldFilterDatesOutsideOfRange() {
        Instant now = Instant.now();
        Date mockFromDate = Date.from(now.minusMillis(5000));
        Date mockToDate = Date.from(now.minusMillis(1000));

        List<DataPoint> mockData = List.of(
                testHelper.mockDataPoint(now.minusMillis(6000).toEpochMilli()), // should be outside range
                testHelper.mockDataPoint(now.minusMillis(4000).toEpochMilli()) // should be inside range
        );

        when(windClient.getStationDataForPeriod(anyString(), anyString())).thenReturn(mockData);
        when(temperatureClient.getStationDataForPeriod(anyString(), anyString())).thenReturn(mockData);

        List<Weather> actual = weatherService.getStationDataForDates("123", mockFromDate, mockToDate);

        assertEquals(1, actual.size());


    }
}