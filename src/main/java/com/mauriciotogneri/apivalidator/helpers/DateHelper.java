package com.mauriciotogneri.apivalidator.helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHelper
{
    private static final DateTimeFormatter defaultFormat1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateTimeFormatter defaultFormat2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final DateTimeFormatter defaultFormat3 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final DateTimeFormatter defaultFormat4 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter defaultFormat5 = DateTimeFormat.forPattern("yyyy-MM-dd");

    private DateHelper()
    {
    }

    public static synchronized String date(DateTime dateTime)
    {
        return defaultFormat1.print(dateTime);
    }

    public static synchronized DateTime date(String value)
    {
        try
        {
            return defaultFormat1.parseDateTime(value);
        }
        catch (Exception e1)
        {
            try
            {
                return defaultFormat2.parseDateTime(value);
            }
            catch (Exception e2)
            {
                try
                {
                    return defaultFormat3.parseDateTime(value);
                }
                catch (Exception e3)
                {
                    try
                    {
                        return defaultFormat4.parseDateTime(value);
                    }
                    catch (Exception e4)
                    {
                        return defaultFormat5.parseDateTime(value);
                    }
                }
            }
        }
    }
}