package com.mauriciotogneri.apivalidator.kernel;

import com.mauriciotogneri.apivalidator.api.ApiRequest;
import com.mauriciotogneri.apivalidator.api.ApiResult;
import com.mauriciotogneri.apivalidator.helpers.JsonHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public abstract class ValidationEndPoint
{
    private final OkHttpClient client;
    private final Logger logger;
    private final String url;
    private final boolean exitOnFail;
    private final List<TestReport> reports = new ArrayList<>();

    protected ValidationEndPoint(OkHttpClient client, Logger logger, String url, boolean exitOnFail)
    {
        this.client = client;
        this.logger = logger;
        this.url = url;
        this.exitOnFail = exitOnFail;
    }

    protected abstract void execute() throws Exception;

    protected void processApiResult(ApiResult result)
    {
    }

    public List<TestReport> check() throws Exception
    {
        logger.log("%n\t» %s:", getClass().getSimpleName());

        execute();

        return reports;
    }

    protected ApiRequest.Builder request(String method)
    {
        return new ApiRequest.Builder(client, url, method);
    }

    protected ApiRequest.Builder request(String url, String method)
    {
        return new ApiRequest.Builder(client, url, method);
    }

    protected ApiResult process(ApiRequest.Builder builder) throws Exception
    {
        long startTime = System.nanoTime();

        ApiRequest apiRequest = builder.build();
        ApiResult apiResult = apiRequest.execute();

        long endTime = System.nanoTime();

        String endpointName = String.format("%s %s ", methodName(), apiRequest.code());
        logger.logLine("\t\t· %s", endpointName);

        String spanSpace = spanSpace(endpointName);
        logger.logLine(spanSpace);

        if (apiResult.isValid())
        {
            addTestReport(new TestReport(true));

            processApiResult(apiResult);

            logger.log(" OK");
        }
        else
        {
            addTestReport(new TestReport(false));

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
        logger.logResponse(apiResult.response(), apiResult.result(), endTime - startTime, apiResult.isValid());

        if (exitOnFail && (!apiResult.isValid()))
        {
            System.exit(-1);
        }

        return apiResult;
    }

    protected RequestBody jsonBody(Object object)
    {
        return RequestBody.create(MediaType.parse("application/json"), JsonHelper.json(object));
    }

    public void checkNotEmpty(Object[] array, String message)
    {
        if (array.length == 0)
        {
            throw new RuntimeException(message);
        }
    }

    public void checkNotEmpty(List<?> list, String message)
    {
        if (list.isEmpty())
        {
            throw new RuntimeException(message);
        }
    }

    private void addTestReport(TestReport report)
    {
        if (!reports.contains(report))
        {
            reports.add(report);
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
        String space = "";
        int limit = 50 - (text.length());

        for (int i = 0; i < limit; i++)
        {
            space += ".";
        }

        return space;
    }
}