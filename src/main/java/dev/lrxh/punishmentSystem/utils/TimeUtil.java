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

        long l = Long.parseLong(timeString.substring(0, timeString.length() - 1));
        if (timeString.endsWith("h")) {
            result = l * TimeUnit.HOURS.toMillis(1);
        } else if (timeString.endsWith("m")) {
            result = l * TimeUnit.MINUTES.toMillis(1);
        } else if (timeString.endsWith("s")) {
            result = l * TimeUnit.SECONDS.toMillis(1);
        } else if (timeString.endsWith("d")) {
            result = l * TimeUnit.DAYS.toMillis(1);
        } else if (timeString.endsWith("y")) {
            result = l * TimeUnit.DAYS.toMillis(365);
        } else {
            throw new IllegalArgumentException("Invalid time string format: " + timeString);
        }

        return result;
    }

    public String unparse(long millis) {
        if (millis < TimeUnit.SECONDS.toMillis(1)) {
            throw new IllegalArgumentException("Time in milliseconds is too small");
        }

        long result;
        String timeString;

        if (millis >= TimeUnit.DAYS.toMillis(365)) {
            result = millis / TimeUnit.DAYS.toMillis(365);
            timeString = result + "year(s)";
        }
        else if (millis >= TimeUnit.DAYS.toMillis(1)) {
            result = millis / TimeUnit.DAYS.toMillis(1);
            timeString = result + "day(s)";
        }
        else if (millis >= TimeUnit.HOURS.toMillis(1)) {
            result = millis / TimeUnit.HOURS.toMillis(1);
            timeString = result + "hour(s)";
        }
        else if (millis >= TimeUnit.MINUTES.toMillis(1)) {
            result = millis / TimeUnit.MINUTES.toMillis(1);
            timeString = result + "minute(s)";
        }
        else {
            result = millis / TimeUnit.SECONDS.toMillis(1);
            timeString = result + "second(s)";
        }

        return timeString;
    }

}
