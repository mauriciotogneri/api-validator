package com.mauriciotogneri.apivalidator.parameters.base;

import java.lang.reflect.Field;

public class ConcatenatedParameters
{
    private final Object object;
    private final Boolean inUrl;

    public ConcatenatedParameters(Object object, Boolean inUrl)
    {
        this.object = object;
        this.inUrl = inUrl;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                Object value = field.get(object);

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
            if (inUrl)
            {
                builder.append("?");
            }
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