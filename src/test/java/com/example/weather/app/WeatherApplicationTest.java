package com.example.weather.app;

import com.example.weather.app.client.WeatherApi;
import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Weather;
import com.example.weather.app.helper.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean(name = "windClient")
    private WeatherApi windClient;

    @MockitoBean(name = "temperatureClient")
    private WeatherApi temperatureClient;

    private final TestHelper testHelper = new TestHelper();

    @ParameterizedTest
    @ValueSource(strings = {"latest-hour", "latest-day", "latest-months"})
    void getForLatestHourShouldReturn200(String timePeriod) {
        String stationId = "333";
        List<DataPoint> mockWinds = testHelper.mockDataPoints(3);
        List<DataPoint> mockTemperatures = testHelper.mockDataPoints(3);

        when(windClient.getStationDataForPeriod(stationId, timePeriod)).thenReturn(mockWinds);
        when(temperatureClient.getStationDataForPeriod(stationId, timePeriod)).thenReturn(mockTemperatures);

        ResponseEntity<List<Weather>> response = this.restTemplate.exchange(
                testHelper.formatUrl(this.port, stationId, timePeriod),
                HttpMethod.GET,
                new HttpEntity<>(testHelper.getHeadersWithApiKey()),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertNotNull(response.getBody());
        List<Weather> weathers = response.getBody();

        assertThat(weathers.size()).isEqualTo(3);
        assertEquals(mockWinds.getFirst().value(), weathers.getFirst().wind());
        assertEquals(mockTemperatures.get(1).value(), weathers.get(1).temperature());
    }
}
