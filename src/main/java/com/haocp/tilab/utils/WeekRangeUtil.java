package com.haocp.tilab.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekRangeUtil {

    public static String formatYearWeek(String yearWeek) {
        int year = Integer.parseInt(yearWeek.substring(0, 4));
        int week = Integer.parseInt(yearWeek.substring(4));

        WeekFields wf = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfWeek = LocalDate.now()
                .withYear(year)
                .with(wf.weekOfYear(), week)
                .with(wf.dayOfWeek(), 1);

        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
        return df.format(firstDayOfWeek) + " - " + df.format(lastDayOfWeek) + "/" + year;
    }

}
