package dev.lrxh.punishmentSystem.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtil {
    private long parseDuration(String time) {
        try {
            if (time.endsWith("s")) {
                return Long.parseLong(time.replace("s", "")) * 20L;
            } else if (time.endsWith("m")) {
                return Long.parseLong(time.replace("m", "")) * 1200L;
            } else if (time.endsWith("h")) {
                return Long.parseLong(time.replace("h", "")) * 72000L;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return -1;
    }
}
