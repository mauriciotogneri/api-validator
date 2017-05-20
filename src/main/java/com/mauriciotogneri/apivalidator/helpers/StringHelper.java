package com.mauriciotogneri.apivalidator.helpers;

public class StringHelper
{
    private StringHelper()
    {
    }

    public static Boolean isEmpty(String value)
    {
        return (value == null) || (value.isEmpty());
    }

    public static Boolean equals(String a, String b)
    {
        return (a != null) && (b != null) && (a.equals(b));
    }
}