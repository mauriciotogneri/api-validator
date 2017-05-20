package com.mauriciotogneri.apivalidator.parameters.url;

import java.lang.reflect.Field;

public class UrlParameter
{
    private final Class<?> clazz;

    public UrlParameter(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                Object value = field.get(this);

                if (value != null)
                {
                    addValue(field.getName(), value.toString(), builder);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return builder.toString();
    }

    private void addValue(String key, String value, StringBuilder builder)
    {
        if (builder.length() == 0)
        {
            builder.append("?");
        }
        else
        {
            builder.append("&");
        }

        builder.append(key);
        builder.append("=");
        builder.append(value);
    }
}