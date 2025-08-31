package com.example.weather.app.utils;

import java.time.Instant;
import java.util.Date;

public class DateUtils {
    public static long toTimestamp(Date date) {
        return date.toInstant().toEpochMilli();
    }

    public static Date fromTimestamp(long timestamp) {
        return Date.from(Instant.ofEpochMilli(timestamp));
    }

    public static boolean isTimestampBetween(long date, long from, long to) {
        return date >= from && date <= to;
    }
}
