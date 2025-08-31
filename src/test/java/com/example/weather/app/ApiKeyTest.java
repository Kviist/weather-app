package com.example.weather.app;

import com.example.weather.app.constants.WeatherTimePeriod;
import com.example.weather.app.helper.TestHelper;
import com.example.weather.app.service.WeatherServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiKeyTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private WeatherServiceImplementation weatherService;

    private final TestHelper testHelper = new TestHelper();

    @Test
    void shouldReturnUnauthorizedWhenThereIsNoApiKey() {
        ResponseEntity<String> response = this.restTemplate.exchange(
                testHelper.formatUrl(this.port, "222", WeatherTimePeriod.LATEST_HOUR.toString()),
                HttpMethod.GET,
                new HttpEntity<>() {
                },
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnUnauthorizedWhenApiKeyIsInvalid() {
        HttpHeaders headers = testHelper.getHeadersWithApiKey("not-a-real-api-key");

        ResponseEntity<String> response = this.restTemplate.exchange(
                testHelper.formatUrl(this.port, "222", WeatherTimePeriod.LATEST_MONTHS.toString()),
                HttpMethod.GET,
                new HttpEntity<>(headers) {
                },
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
