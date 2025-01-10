package com.koma.appparking.services.common;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@UtilityClass
public class ParkingTimeFormatter {

    public static String format(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "start date cannot be null");
        Objects.requireNonNull(end, "end date cannot be null");

        var duration = Duration.between(start, end);
        int totalMinutes = (int) duration.toMinutes();

        if (totalMinutes == 0) {
            return "0 min";
        }

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        if (hours > 0) {
            return String.format("%d h %d min", hours, minutes);
        } else {
            return String.format("%d min", minutes);
        }
    }

}
