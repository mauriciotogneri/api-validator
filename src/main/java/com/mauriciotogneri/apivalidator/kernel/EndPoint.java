package com.mauriciotogneri.apivalidator.kernel;

import com.mauriciotogneri.apivalidator.api.ApiRequest;
import com.mauriciotogneri.apivalidator.api.ApiResult;

import java.io.PrintWriter;
import java.io.StringWriter;

import okhttp3.OkHttpClient;

public class EndPoint
{
    private final OkHttpClient client;
    private final Logger logger;
    private final String url;
    private final String method;

    protected EndPoint(OkHttpClient client, Logger logger, String url, String method)
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

        if (!apiResult.isValid())
        {
            logger.error("%n%s", apiResult.error());

            if (apiResult.response() != null)
            {
                logger.error("%n%s%n", apiResult.response().toString());
            }

            StringWriter errors = new StringWriter();
            new Exception().printStackTrace(new PrintWriter(errors));
            logger.error(errors.toString());
        }

        logger.logRequest(apiRequest.request(), apiResult.isValid());
        logger.logResponse(apiResult.response(), apiResult.string(), endTime - startTime, apiResult.isValid());

        return apiResult;
    }
}