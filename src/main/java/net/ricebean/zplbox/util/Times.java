package net.ricebean.zplbox.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Times {

    /**
     * Helper method for converting milli seconds to a human readable form.
     *
     * @param millis The millis to be converted.
     * @return Timestamp as human-readable text.
     */
    public static String millis2readable(long millis) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME);
    }


    /**
     * Helper method for converting a duration in a human readable string.
     *
     * @param millis The duration as milliseconds.
     * @return The duration as human-readable string.
     */
    public static String duration2readable(long millis) {
        long seconds = Math.abs(millis / 1000);

        String duration = String.format(
                "%dd %02dh %02dm %02ds",
                seconds / (3600 * 24),
                (seconds % (3600 * 24)) / 3600,
                (seconds % 3600) / 60,
                seconds % 60);

        if (duration.startsWith("0d ")) {
            duration = duration.substring(3);
        }

        return duration;
    }

}
