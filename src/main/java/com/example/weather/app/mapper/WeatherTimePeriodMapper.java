package com.example.weather.app.mapper;

import com.example.weather.app.constants.WeatherTimePeriod;

public class WeatherTimePeriodMapper {
    public static String toString(WeatherTimePeriod timePeriod) {
        return switch (timePeriod) {
            case LATEST_HOUR -> "latest-hour";
            case LATEST_DAY -> "latest-day";
            case LATEST_MONTHS -> "latest-months";
        };
    }
}
