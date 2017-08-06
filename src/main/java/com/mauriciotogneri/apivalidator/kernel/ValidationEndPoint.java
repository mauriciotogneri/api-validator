package com.mauriciotogneri.apivalidator.kernel;

import com.mauriciotogneri.apivalidator.api.ApiRequest;
import com.mauriciotogneri.apivalidator.api.ApiResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import okhttp3.OkHttpClient;

public class ValidationEndPoint
{
    private final OkHttpClient client;
    private final Logger logger;
    private final String url;
    private final String method;

    protected ValidationEndPoint(OkHttpClient client, Logger logger, String url, String method)
    {
        this.client = client;
        this.logger = logger;
        this.url = url;
        this.method = method;
    }

    protected ApiRequest.Builder request()
    {
        return new ApiRequest.Builder(client, method, url);
    }

    protected ApiRequest.Builder request(String url, String method)
    {
        return new ApiRequest.Builder(client, method, url);
    }

    protected ApiResult process(ApiRequest.Builder builder) throws Exception
    {
        Long startTime = System.nanoTime();

        ApiRequest apiRequest = builder.build();
        ApiResult apiResult = apiRequest.execute();

        Long endTime = System.nanoTime();

        String endpointName = String.format("%s (%d) ", methodName(), apiRequest.code());
        logger.logLine("\t\t· %s", endpointName);

        String spanSpace = spanSpace(endpointName);
        logger.logLine(spanSpace);

        if (apiResult.isValid())
        {
            logger.log(" OK");
        }
        else
        {
            logger.error("%n%n%s", apiResult.error());

            if (apiResult.response() != null)
            {
                logger.error("%n%s%n", apiResult.response().toString());
            }

            StringWriter errors = new StringWriter();
            new Exception().printStackTrace(new PrintWriter(errors));
            String stackTrace = errors.toString();
            logger.error(stackTrace);
        }

        logger.logRequest(apiRequest.request(), apiResult.isValid());
        logger.logResponse(apiResult.response(), apiResult.string(), endTime - startTime, apiResult.isValid());

        return apiResult;
    }

    protected void checkNotEmpty(Object[] array, String message)
    {
        if (array.length == 0)
        {
            throw new RuntimeException(message);
        }
    }

    protected void checkNotEmpty(Collection<?> list, String message)
    {
        if (list.isEmpty())
        {
            throw new RuntimeException(message);
        }
    }

    private String methodName()
    {
        try
        {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            return stackTrace[2].getMethodName();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private String spanSpace(String text)
    {
        StringBuilder space = new StringBuilder();
        int limit = 50 - (text.length());

        for (int i = 0; i < limit; i++)
        {
            space.append(".");
        }

        return space.toString();
    }
}