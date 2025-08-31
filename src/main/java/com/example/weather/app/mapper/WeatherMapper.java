package com.example.weather.app.mapper;

import com.example.weather.app.dto.Weather;
import com.example.weather.app.model.DataPoint;
import com.example.weather.app.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherMapper {
    /**
     * Takes two list of data points and merges them to weather objects
     * The data point lists must be of the same size
     * Will only merge data points where the date is the same
     *
     * @param winds        list of wind measurements on a station
     * @param temperatures list of temperature measurements on a stations
     * @return mapped list of weather objects containing both wind and temperature data
     */
    public static List<Weather> mergeDataPointsToWeather(List<DataPoint> winds, List<DataPoint> temperatures) {
        if (!(winds.size() == temperatures.size())) {
            throw new RuntimeException("Cannot map lists as there are not of the same size");
        }

        DataPoint wind;
        DataPoint temp;
        List<Weather> weathers = new ArrayList<>();

        for (int i = 0; i < winds.size(); i++) {
            wind = winds.get(i);
            temp = temperatures.get(i);

            if (wind.date().equals(temp.date())) {
                weathers.add(mapToWeather(wind, temp));
            }
        }

        return weathers;
    }

    private static Weather mapToWeather(DataPoint wind, DataPoint temperature) {
        return new Weather(DateUtils.fromTimestamp(wind.date()), wind.value(), temperature.value());
    }
}
