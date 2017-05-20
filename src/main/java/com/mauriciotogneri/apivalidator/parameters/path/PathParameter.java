package com.mauriciotogneri.apivalidator.parameters.path;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class PathParameter extends HashMap<String, String> implements Iterable<Entry<String, String>>
{
    public PathParameter(Class<?> clazz)
    {
        fill(clazz);
    }

    private void fill(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                Object value = field.get(this);

                if (value != null)
                {
                    put(field.getName(), value.toString());
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Iterator<Entry<String, String>> iterator()
    {
        return entrySet().iterator();
    }
}