package com.example.weather.app.constants;

/**
 * Each api parameter is represented by a number in the external api
 */
public enum WeatherParameter {
    WIND(1),
    TEMPERATURE(21);

    public final int number;

    WeatherParameter(int number) {
        this.number = number;
    }
}