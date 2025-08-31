package com.example.weather.app.mapper;

import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Weather;
import com.example.weather.app.helper.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherMapperTest {

    private final TestHelper testHelper = new TestHelper();

    @Test
    void shouldThrowExceptionWhenListsAreDifferentSize() {
        List<DataPoint> winds = testHelper.mockDataPoints(3);
        List<DataPoint> temps = testHelper.mockDataPoints(4);
        assertThrows(RuntimeException.class, () -> WeatherMapper.mergeDataPointsToWeather(winds, temps));
    }

    @Test
    void shouldMapWindsAndTemperaturesCorrectly() {
        List<DataPoint> winds = testHelper.mockDataPoints(3);
        List<DataPoint> temps = testHelper.mockDataPoints(3);
        List<Weather> weathers = WeatherMapper.mergeDataPointsToWeather(winds, temps);

        for (int i = 0; i < 3; i++) {
            assertEquals(winds.get(i).value(), weathers.get(i).wind());
            assertEquals(temps.get(i).value(), weathers.get(i).temperature());
        }
    }

    @Test
    void shouldOnlyIncludeDataPointsWithTheSameDate() {
        List<DataPoint> winds = List.of(testHelper.mockDataPoint(123), testHelper.mockDataPoint(456));
        List<DataPoint> temps = List.of(testHelper.mockDataPoint(123), testHelper.mockDataPoint(999));
        List<Weather> weathers = WeatherMapper.mergeDataPointsToWeather(winds, temps);
        assertEquals(1, weathers.size());
    }
}
