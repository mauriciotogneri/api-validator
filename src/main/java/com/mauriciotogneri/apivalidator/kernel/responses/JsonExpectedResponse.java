package com.mauriciotogneri.apivalidator.kernel.responses;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.gson.JsonObject;
import com.mauriciotogneri.apivalidator.api.ApiResult;
import com.mauriciotogneri.jsonschema.SchemaValidator;

import java.util.Iterator;

import okhttp3.Response;

public class JsonExpectedResponse extends ExpectedResponse
{
    private final JsonObject schema;

    public JsonExpectedResponse(Integer code, Class<?> clazz)
    {
        super(code);
        this.schema = new JsonObject();
    }

    @Override
    public ApiResult validate(Response response, String body) throws Exception
    {
        String result = body(response);

        ProcessingReport report = validate(result, schema);

        if (report.isSuccess())
        {
            return ApiResult.valid(result, response);
        }
        else
        {
            return ApiResult.error(result, response, report.toString());
        }
    }

    private ProcessingReport validate(String jsonData, JsonObject jsonSchema)
    {
        try
        {
            SchemaValidator schemaValidator = new SchemaValidator(jsonSchema);

            return schemaValidator.validate(jsonData);
        }
        catch (final Exception e)
        {
            return new ProcessingReport()
            {
                @Override
                public Iterator<ProcessingMessage> iterator()
                {
                    return null;
                }

                @Override
                public LogLevel getLogLevel()
                {
                    return null;
                }

                @Override
                public LogLevel getExceptionThreshold()
                {
                    return null;
                }

                @Override
                public void debug(ProcessingMessage processingMessage) throws ProcessingException
                {
                }

                @Override
                public void info(ProcessingMessage processingMessage) throws ProcessingException
                {
                }

                @Override
                public void warn(ProcessingMessage processingMessage) throws ProcessingException
                {
                }

                @Override
                public void error(ProcessingMessage processingMessage) throws ProcessingException
                {
                }

                @Override
                public void fatal(ProcessingMessage processingMessage) throws ProcessingException
                {
                }

                @Override
                public boolean isSuccess()
                {
                    return false;
                }

                @Override
                public void mergeWith(ProcessingReport processingReport) throws ProcessingException
                {
                }

                @Override
                public String toString()
                {
                    return e.getLocalizedMessage();
                }
            };
        }
    }
}