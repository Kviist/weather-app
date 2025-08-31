package com.example.weather.app.helper;

import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Station;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestHelper {
    private final Random random;

    public TestHelper() {
        this.random = new Random();
    }

    public String formatUrl(int port, String stationId, String timePeriod) {
        return String.format("http://localhost:%s/api/weather/station/%s/%s", port, stationId, timePeriod);
    }

    public List<DataPoint> mockDataPoints(int points) {
        return LongStream.range(0, points).mapToObj(this::mockDataPoint).toList();
    }

    public DataPoint mockDataPoint(long date) {
        return new DataPoint(date, String.valueOf(this.random.nextInt()), String.valueOf(this.random.nextInt()));
    }

    public List<Station> mockStations(int count) {
        return IntStream.range(0, count).mapToObj(i -> mockStation()).toList();
    }

    public Station mockStation() {
        String key = String.valueOf(random.nextInt());
        return new Station(key, key + "-mock-station");
    }

    public HttpHeaders getHeadersWithApiKey(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", apiKey);
        return headers;
    }

    public HttpHeaders getHeadersWithApiKey() {
        return getHeadersWithApiKey("secret-api-key");
    }
}
