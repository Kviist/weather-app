package com.example.weather.app.service;

import com.example.weather.app.client.WeatherApi;
import com.example.weather.app.constants.WeatherTimePeriod;
import com.example.weather.app.dto.Station;
import com.example.weather.app.dto.Weather;
import com.example.weather.app.mapper.WeatherMapper;
import com.example.weather.app.mapper.WeatherTimePeriodMapper;
import com.example.weather.app.model.DataPoint;
import com.example.weather.app.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WeatherServiceImplementation implements WeatherService {
    private final WeatherApi windClient;
    private final WeatherApi temperatureClient;
    private final Logger logger = LoggerFactory.getLogger(WeatherServiceImplementation.class);

    public WeatherServiceImplementation(
            @Qualifier("windClient") WeatherApi windClient,
            @Qualifier("temperatureClient") WeatherApi temperatureClient
    ) {
        this.windClient = windClient;
        this.temperatureClient = temperatureClient;
    }

    /**
     * Here we assume that both the wind and temperature parameters give the same list of stations and only use one
     *
     * @return list of all stations available in the api
     */
    @Override
    public List<Station> getAllStations() {
        logger.info("Fetching all stations");
        return windClient.getAllStations();
    }

    /**
     * Fetches wind and temperature measurements for a given station id and time period
     *
     * @param stationId  id of the station to fetch data on
     * @param timePeriod one of the available time periods in the api
     * @return list of mapped weather objects
     */
    @Override
    public List<Weather> getStationDataForPeriod(String stationId, WeatherTimePeriod timePeriod) {
        logger.info("Processing weather for station {} during time period {}", stationId, timePeriod);
        String time = WeatherTimePeriodMapper.toString(timePeriod);
        List<DataPoint> winds = windClient.getStationDataForPeriod(stationId, time);
        List<DataPoint> temperatures = temperatureClient.getStationDataForPeriod(stationId, time);
        return WeatherMapper.mergeDataPointsToWeather(winds, temperatures);
    }

    /**
     * Fetches wind and temperature measurements for a given station id, start date and end date
     *
     * @param stationId id of the station to fetch data on
     * @param from      date to start searching on
     * @param to        data to end searching on
     * @return list of mapped weather objects
     */
    @Override
    public List<Weather> getStationDataForDates(String stationId, Date from, Date to) {
        logger.info("Processing weather for station {} from {} to {}", stationId, from, to);
        String time = WeatherTimePeriodMapper.toString(WeatherTimePeriod.LATEST_MONTHS);
        List<DataPoint> winds = windClient.getStationDataForPeriod(stationId, time);
        List<DataPoint> temperatures = temperatureClient.getStationDataForPeriod(stationId, time);
        List<DataPoint> filteredWinds = filterBetweenDates(winds, from, to);
        List<DataPoint> filteredTemperatures = filterBetweenDates(temperatures, from, to);
        return WeatherMapper.mergeDataPointsToWeather(filteredWinds, filteredTemperatures);
    }

    private List<DataPoint> filterBetweenDates(List<DataPoint> dataPoints, Date from, Date to) {
        long fromTimestamp = DateUtils.toTimestamp(from);
        long toTimestamp = DateUtils.toTimestamp(to);
        return dataPoints.stream()
                .filter(dataPoint -> DateUtils.isTimestampBetween(dataPoint.date(), fromTimestamp, toTimestamp))
                .toList();
    }
}
