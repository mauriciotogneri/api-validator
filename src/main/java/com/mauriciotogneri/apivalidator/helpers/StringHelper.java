package com.mauriciotogneri.apivalidator.helpers;

public class StringHelper
{
    private StringHelper()
    {
    }

    public static boolean isEmpty(String value)
    {
        return (value == null) || (value.isEmpty());
    }

    public static boolean equals(String a, String b)
    {
        return (a != null) && (b != null) && (a.equals(b));
    }
}