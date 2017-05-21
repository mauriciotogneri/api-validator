package com.mauriciotogneri.apivalidator.parameters.url;

import java.lang.reflect.Field;

public class UrlParameter
{
    private final Object object;

    public UrlParameter(Object object)
    {
        this.object = object;
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