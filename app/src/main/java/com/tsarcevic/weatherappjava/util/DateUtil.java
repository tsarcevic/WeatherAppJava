package com.tsarcevic.weatherappjava.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String showHourMinuteFormat(String date) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(date.substring(11, 16));
        stringBuilder.append(" - ");
        stringBuilder.append(date.substring(8, 10));
        stringBuilder.append(". ");
        stringBuilder.append(date.substring(5, 7));
        stringBuilder.append(".");

        return stringBuilder.toString();
    }
}
