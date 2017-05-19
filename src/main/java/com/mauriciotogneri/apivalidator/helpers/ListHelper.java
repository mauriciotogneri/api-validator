package com.mauriciotogneri.apivalidator.helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListHelper
{
    private ListHelper()
    {
    }

    public static <T> T random(List<T> list)
    {
        return list.get(RandomHelper.nextInt(list.size()));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] asArray(List<T> list, Class<T> clazz)
    {
        T[] result = (T[]) Array.newInstance(clazz, list.size());

        list.toArray(result);

        return result;
    }

    public static <T> List<T> split(String list, Factory<T> factory)
    {
        List<T> result = new ArrayList<>();

        if (list != null)
        {
            String[] parts = list.split(",");

            for (String part : parts)
            {
                T element;

                try
                {
                    element = factory.create(part);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }

                result.add(element);
            }
        }

        return result;
    }

    public static <T> String join(List<T> list, String separator)
    {
        StringBuilder builder = new StringBuilder();

        for (T element : list)
        {
            if (builder.length() != 0)
            {
                builder.append(separator);
            }

            builder.append(element.toString());
        }

        return builder.toString();
    }

    public interface Factory<T>
    {
        T create(String input) throws Exception;
    }
}