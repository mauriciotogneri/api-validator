package com.mauriciotogneri.apivalidator.responses;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.gson.JsonObject;
import com.mauriciotogneri.apivalidator.api.ApiResult;
import com.mauriciotogneri.jsonschema.JsonSchema;
import com.mauriciotogneri.jsonschema.SchemaValidator;

import java.util.Iterator;

import okhttp3.Response;

public class JsonExpectedResponse extends ExpectedResponse
{
    private final JsonObject schema;

    public JsonExpectedResponse(Integer code, Class<?> clazz)
    {
        super(code);
        this.schema = new JsonSchema(clazz).schema();
    }

    @Override
    public ApiResult validate(Response response, String body) throws Exception
    {
        ProcessingReport report = validate(body, schema);

        if (report.isSuccess())
        {
            return ApiResult.valid(response, body);
        }
        else
        {
            return ApiResult.error(response, body, report.toString());
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