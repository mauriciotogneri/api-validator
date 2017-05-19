package com.mauriciotogneri.apivalidator.helpers;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ResourceHelper
{
    private ResourceHelper()
    {
    }

    public static String asString(InputStream inputStream)
    {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
    }

    public static byte[] asBytes(InputStream inputStream) throws IOException
    {
        int read;
        int size = 1024;
        byte[] buf = new byte[size];

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while ((read = inputStream.read(buf, 0, size)) != -1)
        {
            bos.write(buf, 0, read);
        }

        return bos.toByteArray();
    }

    public static String asString(String pattern, Object... parameters)
    {
        return asString(inputStream(String.format(pattern, parameters)));
    }

    public static byte[] asBytes(String pattern, Object... parameters) throws IOException
    {
        return asBytes(inputStream(String.format(pattern, parameters)));
    }

    public static InputStream inputStream(String path)
    {
        ClassLoader classLoader = ResourceHelper.class.getClassLoader();

        return classLoader.getResourceAsStream(path);
    }

    public static void save(File file, String content) throws IOException
    {
        File parent = file.getParentFile();

        if ((parent.exists() || parent.mkdirs()) && (file.exists() || file.createNewFile()))
        {
            try (FileWriter fileWriter = new FileWriter(file.getAbsoluteFile()))
            {
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(content);
                bw.close();
            }
        }
    }

    public static boolean close(Closeable resource)
    {
        boolean result = false;

        if (resource != null)
        {
            try
            {
                resource.close();
                result = true;
            }
            catch (Exception e)
            {
                // exception swallowed on purpose
            }
        }

        return result;
    }
}