package com.mauriciotogneri.apivalidator.kernel;

import com.mauriciotogneri.apivalidator.helpers.StringHelper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class Logger
{
    private final boolean consoleLogs;
    private final BufferedWriter fileLog;

    public Logger(boolean consoleLogs, String filePath) throws Exception
    {
        this.consoleLogs = consoleLogs;
        this.fileLog = fileLog(filePath);
    }

    private BufferedWriter fileLog(String filePath) throws Exception
    {
        if (!filePath.isEmpty() && !StringHelper.equals(filePath, "-"))
        {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dtf.print(new DateTime());

            File parent = new File(filePath);
            File file = new File(parent, String.format("%s.log", timestamp));

            if ((!parent.exists()) && (!parent.mkdirs()))
            {
                throw new IOException(String.format("Cannot create folder: %s", parent.getAbsolutePath()));
            }

            if ((!file.exists()) && (!file.createNewFile()))
            {
                throw new IOException(String.format("Cannot create file: %s", file.getAbsolutePath()));
            }

            FileWriter fileWriter = new FileWriter(file, true);

            return new BufferedWriter(fileWriter);
        }

        return null;
    }

    public void log(String format, Object... text)
    {
        String log = text(format, text);

        if (consoleLogs)
        {
            System.out.println(log);
            System.out.flush();
        }

        writeToFile(log, true);
    }

    public void logLine(String format, Object... text)
    {
        String log = text(format, text);

        if (consoleLogs)
        {
            System.out.print(log);
            System.out.flush();
        }

        writeToFile(log, false);
    }

    public void network(boolean isValid, String format, Object... text)
    {
        String log = text(format, text);

        if (!isValid)
        {
            System.err.println(log);
            System.err.flush();
        }

        writeToFile(log, true);
    }

    public void error(String format, Object... text)
    {
        String log = text(format, text);

        if (consoleLogs)
        {
            System.err.println(log);
            System.err.flush();
        }

        writeToFile(log, true);
    }

    private void writeToFile(String log, boolean newLine)
    {
        if (fileLog != null)
        {
            try
            {
                fileLog.append(log);

                if (newLine)
                {
                    fileLog.newLine();
                }

                fileLog.flush();
            }
            catch (Exception e)
            {
                // log cannot be written in the file
                e.printStackTrace();
            }
        }
    }

    private String text(String format, Object... text)
    {
        return String.format(format, text);
    }

    public void logRequest(Request request, boolean isValid) throws IOException
    {
        network(isValid, "%n>>> %s %s%n", request.method(), request.url());
        network(isValid, "%s", request.headers());

        Buffer buffer = new Buffer();
        RequestBody requestBody = request.body();

        if (requestBody != null)
        {
            requestBody.writeTo(buffer);

            network(isValid, "%s%n", buffer.readUtf8());
        }
    }

    public void logResponse(Response response, String result, long totalTime, boolean isValid)
    {
        if (response != null)
        {
            network(isValid, "<<< %s %s (%d ms)%n", response.code(), response.request().url(), (int) (totalTime / 1e6d));
            network(isValid, "%s", response.headers());
        }

        if (!result.isEmpty())
        {
            network(isValid, "%s%n", result);
        }

        network(isValid, "====================================================================================================%n");
    }
}