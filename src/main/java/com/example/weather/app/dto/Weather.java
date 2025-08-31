package com.example.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record Weather(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") Date date,
        String wind,
        String temperature
) {
}
