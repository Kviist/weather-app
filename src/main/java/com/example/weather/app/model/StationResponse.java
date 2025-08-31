package com.example.weather.app.model;

import com.example.weather.app.dto.Station;

import java.util.List;

public record StationResponse(List<Station> station) {
}
