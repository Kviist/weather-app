package com.example.weather.app.controller;

import com.example.weather.app.constants.WeatherTimePeriod;
import com.example.weather.app.dto.Station;
import com.example.weather.app.dto.Weather;
import com.example.weather.app.service.WeatherServiceImplementation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherServiceImplementation weatherService;

    public WeatherController(WeatherServiceImplementation weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/stations")
    public List<Station> getAllStations() {
        return weatherService.getAllStations();
    }

    @GetMapping("/station/{id}/latest-hour")
    public List<Weather> getStationLatestHour(@PathVariable String id) {
        return weatherService.getStationDataForPeriod(id, WeatherTimePeriod.LATEST_HOUR);
    }

    @GetMapping("/station/{id}/latest-day")
    public List<Weather> getStationLatestDay(@PathVariable String id) {
        return weatherService.getStationDataForPeriod(id, WeatherTimePeriod.LATEST_DAY);
    }

    @GetMapping("/station/{id}/latest-months")
    public List<Weather> getStationLatestMonths(@PathVariable String id) {
        return weatherService.getStationDataForPeriod(id, WeatherTimePeriod.LATEST_MONTHS);
    }

    @GetMapping("/station/{id}/filter")
    public List<Weather> getStationFilterByDates(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to
    ) {
        return weatherService.getStationDataForDates(id, from, to);
    }

}
