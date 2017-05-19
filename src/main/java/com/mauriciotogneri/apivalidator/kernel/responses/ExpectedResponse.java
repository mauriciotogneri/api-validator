package com.mauriciotogneri.apivalidator.kernel.responses;

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

    public String code()
    {
        return String.format("(%d)", code);
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
                        body,
                        response,
                        "Expected non empty response but received no data");
            }
            else if (!isEmpty(body) && shouldBeEmpty())
            {
                return ApiResult.error(
                        body,
                        response,
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
                    body,
                    response,
                    String.format("Expected response code %s but received %s", code, responseCode));
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