package com.mauriciotogneri.apivalidator.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberHelper
{
    private NumberHelper()
    {
    }

    public static Integer randomInt(Integer places)
    {
        return RandomHelper.nextInt(places);
    }

    public static Double randomAsDecimal(Integer places)
    {
        return round(RandomHelper.nextDouble() * Math.pow(10, places), 2);
    }

    public static Double randomPercentage()
    {
        return round(RandomHelper.nextDouble() * RandomHelper.sign(), 2);
    }

    public static Double round(Double value, Integer places)
    {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}