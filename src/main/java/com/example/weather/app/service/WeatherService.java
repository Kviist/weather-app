package com.example.weather.app.service;

import com.example.weather.app.constants.WeatherTimePeriod;
import com.example.weather.app.dto.Station;
import com.example.weather.app.dto.Weather;

import java.util.Date;
import java.util.List;

public interface WeatherService {
    List<Station> getAllStations();
    List<Weather> getStationDataForPeriod(String stationId, WeatherTimePeriod timePeriod);
    List<Weather> getStationDataForDates(String stationId, Date from, Date to);
}
