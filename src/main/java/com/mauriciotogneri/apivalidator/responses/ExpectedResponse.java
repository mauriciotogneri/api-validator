package com.mauriciotogneri.apivalidator.responses;

import com.mauriciotogneri.apivalidator.api.ApiResult;

import java.io.IOException;

import okhttp3.Response;

public abstract class ExpectedResponse
{
    private final Integer code;

    protected ExpectedResponse(Integer code)
    {
        this.code = code;
    }

    public Integer code()
    {
        return code;
    }

    private Boolean isEmpty(String body)
    {
        return ((body == null) || body.isEmpty());
    }

    public ApiResult check(Response response) throws Exception
    {
        Integer responseCode = response.code();
        String body = body(response);

        if (code.equals(responseCode))
        {
            if (isEmpty(body) && !shouldBeEmpty())
            {
                return ApiResult.error(
                        response,
                        body,
                        "Expected non empty response but received no data");
            }
            else if (!isEmpty(body) && shouldBeEmpty())
            {
                return ApiResult.error(
                        response,
                        body,
                        "Expected empty response but received data");
            }
            else
            {
                return validate(response, body);
            }
        }
        else
        {
            return ApiResult.error(
                    response,
                    body,
                    String.format("Expected response code %d but received %s", code, responseCode));
        }
    }

    protected String body(Response response) throws IOException
    {
        return new String(response.body().bytes(), "UTF-8");
    }

    protected Boolean shouldBeEmpty()
    {
        return false;
    }

    public abstract ApiResult validate(Response response, String body) throws Exception;
}