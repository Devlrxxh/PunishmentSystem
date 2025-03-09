package dev.lrxh.punishmentSystem.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUtil {
    public long parse(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            throw new IllegalArgumentException("Time string cannot be null or empty");
        }

        long result;

        if (timeString.endsWith("h")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 1)) * TimeUnit.HOURS.toMillis(1);
        } else if (timeString.endsWith("m")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 1)) * TimeUnit.MINUTES.toMillis(1);
        } else if (timeString.endsWith("s")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 1)) * TimeUnit.SECONDS.toMillis(1);
        } else if (timeString.endsWith("d")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 1)) * TimeUnit.DAYS.toMillis(1);
        } else if (timeString.endsWith("y")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 1)) * TimeUnit.DAYS.toMillis(365); // Approximate 1 year
        } else if (timeString.endsWith("mo")) {
            result = Long.parseLong(timeString.substring(0, timeString.length() - 2)) * TimeUnit.DAYS.toMillis(30); // Approximate 1 month
        } else {
            throw new IllegalArgumentException("Invalid time string format: " + timeString);
        }

        return result;
    }
}
