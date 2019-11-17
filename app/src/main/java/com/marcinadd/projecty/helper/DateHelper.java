package com.marcinadd.projecty.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date parseDate(String string) throws ParseException {
        return dateFormat.parse(string);
    }
}
