package com.example.weather.app.client;

import com.example.weather.app.model.DataPoint;
import com.example.weather.app.dto.Station;

import java.util.List;

public interface WeatherApi {
    List<Station> getAllStations();
    List<DataPoint> getStationDataForPeriod(String stationId, String timePeriod);
}
