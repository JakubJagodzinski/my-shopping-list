package server;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeManager {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String parseToDate(long timeInMillis) {
        Instant instant = Instant.ofEpochMilli(timeInMillis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(dateFormatter);
    }

    public static long getTimeDiff(long timeInMillis) {
        return System.currentTimeMillis() - timeInMillis;
    }

    public static String parseToTime(long timeInMillis) {
        long hr = timeInMillis / 3600000;
        long min = (timeInMillis % 3600000) / 60000;
        long s = ((timeInMillis % 3600000) % 60000) / 1000;
        return hr + " hours, " + min + " minutes, " + s + " seconds";
    }

}
