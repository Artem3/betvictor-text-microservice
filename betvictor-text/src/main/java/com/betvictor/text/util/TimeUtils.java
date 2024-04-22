package com.betvictor.text.util;

import lombok.experimental.UtilityClass;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUtils {
    /**
     * Formats the given duration in nanoseconds to a human-readable format.
     * @param nanoSeconds The duration in nanoseconds.
     * @return A formatted string representing the duration in the most suitable time units.
     */
    public String formatDuration(long nanoSeconds) {
        long minutes = TimeUnit.NANOSECONDS.toMinutes(nanoSeconds);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoSeconds) % 60;
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(nanoSeconds) % 1000;
        long microseconds = TimeUnit.NANOSECONDS.toMicros(nanoSeconds) % 1000;
        long nanoseconds = nanoSeconds % 1000;

        if (minutes > 0) {
            return String.format("%dm %ds %dms", minutes, seconds, milliseconds);
        } else if (seconds > 0) {
            return String.format("%ds %dms", seconds, milliseconds);
        } else if (milliseconds > 0) {
            return String.format("%dms %dμs", milliseconds, microseconds);
        } else if (microseconds > 0) {
            return String.format("%dμs %dns", microseconds, nanoseconds);
        } else {
            return String.format("%dns", nanoseconds);
        }
    }
}
