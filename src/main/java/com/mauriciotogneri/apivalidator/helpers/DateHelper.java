package com.mauriciotogneri.apivalidator.helpers;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class DateHelper
{
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private DateHelper()
    {
    }

    // ============================================================================================

    public static String date(DateTime dateTime, DateTimeZone timeZone, Locale locale, String defaultValue)
    {
        if ((dateTime != null) && (timeZone != null) && (locale != null))
        {
            return formatter.withZone(timeZone).withLocale(locale).print(dateTime);
        }
        else
        {
            return defaultValue;
        }
    }

    public static String date(DateTime dateTime, Locale locale, String defaultValue)
    {
        return date(dateTime, DateTimeZone.getDefault(), locale, defaultValue);
    }

    public static String date(DateTime dateTime, DateTimeZone timeZone, Locale locale)
    {
        return date(dateTime, timeZone, locale, null);
    }

    public static String date(DateTime dateTime, Locale locale)
    {
        return date(dateTime, DateTimeZone.getDefault(), locale, null);
    }

    // ============================================================================================

    public static DateTime date(String timestamp, DateTimeZone timeZone, DateTime defaultValue)
    {
        try
        {
            return formatter.withZone(timeZone).parseDateTime(timestamp);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    public static DateTime date(String timestamp, DateTime defaultValue)
    {
        return date(timestamp, DateTimeZone.getDefault(), defaultValue);
    }

    public static DateTime date(String timestamp, DateTimeZone timeZone)
    {
        return date(timestamp, timeZone, null);
    }

    public static DateTime date(String timestamp)
    {
        return date(timestamp, DateTimeZone.getDefault(), null);
    }
}