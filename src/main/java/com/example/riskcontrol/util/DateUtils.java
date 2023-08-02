package com.example.riskcontrol.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /** Constant <code>dateFormat</code> */
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /** Constant <code>dateTimeFormat</code> */
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date parseDate(String str) throws ParseException {
		if (str == null || str.trim().equals("")) {
		    return null;
		}
		return dateFormat.parse(str);
	}

    public static Date parseDateTime(String str) throws ParseException {
        if (str == null || str.trim().equals("")) {
            return null;
        }
        return dateTimeFormat.parse(str);
    }


    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static long dateSubtract(Date startDate,Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        return (startCal.getTimeInMillis() - endCal.getTimeInMillis()) / (1000);
    }

    public static Date addDate(int day,Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Calendar parse(String source, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
           Date date = dateFormat.parse(source);
           c.setTime(date);
        } catch (ParseException e) {
            c = null;
        }
        return c;

    }

    public static Calendar addDate(Date d, int field, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(field, value);
        return c;
    }


}
