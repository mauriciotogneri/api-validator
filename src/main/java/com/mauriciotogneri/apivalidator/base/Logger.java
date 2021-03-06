package com.mauriciotogneri.apivalidator.base;

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
    private final BufferedWriter fileLog;

    public Logger(String filePath) throws Exception
    {
        this.fileLog = fileLog(filePath);
    }

    private BufferedWriter fileLog(String filePath) throws Exception
    {
        if ((filePath != null) && (!filePath.isEmpty()))
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
        String log = String.format(format, text);

        writeToFile(log, true);
    }

    public void logLine(String format, Object... text)
    {
        String log = String.format(format, text);

        writeToFile(log, false);
    }

    public void network(String format, Object... text)
    {
        writeToFile(String.format(format, text), true);
    }

    public void error(String format, Object... text)
    {
        String log = String.format(format, text);

        writeToFile(log, true);
    }

    private void writeToFile(String log, Boolean newLine)
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

    public void logRequest(Request request) throws IOException
    {
        network("%n>>> %s %s", request.method(), request.url());
        network("%s", request.headers());

        Buffer buffer = new Buffer();
        RequestBody requestBody = request.body();

        if (requestBody != null)
        {
            requestBody.writeTo(buffer);

            network("%s%n", buffer.readUtf8());
        }
    }

    public void logResponse(Response response, String result, Long totalTime)
    {
        if (response != null)
        {
            network("<<< %s %s (%d ms)", response.code(), response.request().url(), (int) (totalTime / 1e6d));
            network("%s", response.headers());
        }

        if (!result.isEmpty())
        {
            network("%s%n", result);
        }

        network("====================================================================================================");
    }
}