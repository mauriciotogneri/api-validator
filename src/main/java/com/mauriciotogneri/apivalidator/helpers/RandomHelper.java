package com.mauriciotogneri.apivalidator.helpers;

import java.util.Random;

public class RandomHelper
{
    private RandomHelper()
    {
    }

    // 3 out of 5 => [ 0 1 2 ] 3 4
    public static Boolean chances(Integer valid, Integer outOf)
    {
        return (new Random().nextInt(outOf) < valid);
    }

    public static Boolean chance(Integer outOf)
    {
        return (new Random().nextInt(outOf) == 0);
    }

    public static Integer get(Integer min, Integer max)
    {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static Integer nextInt(Integer max)
    {
        return new Random().nextInt(max);
    }

    public static Double nextDouble()
    {
        return NumberHelper.round(new Random().nextDouble(), 2);
    }

    public static Double nextSignedDouble()
    {
        return NumberHelper.round(nextDouble() * sign(), 2);
    }

    public static Double nextDouble(Double max)
    {
        return NumberHelper.round(new Random().nextDouble() * max, 2);
    }

    public static Double nextSignedDouble(Double max)
    {
        return NumberHelper.round(nextDouble(max) * sign(), 2);
    }

    public static Integer sign()
    {
        return (new Random().nextBoolean() ? 1 : -1);
    }

    public static Boolean nextBoolean()
    {
        return new Random().nextBoolean();
    }
}