package com.mauriciotogneri.apivalidator.api;

import java.io.IOException;

import okhttp3.Response;

public class ApiResult
{
    private final Boolean valid;
    private final Response response;
    private final String string;
    private final String error;

    private ApiResult(Boolean valid, Response response, String string, String error)
    {
        this.valid = valid;
        this.response = response;
        this.string = string;
        this.error = error;
    }

    public Boolean isValid()
    {
        return valid;
    }

    public Response response()
    {
        return response;
    }

    public String string()
    {
        return string;
    }

    public byte[] bytes() throws IOException
    {
        return string().getBytes();
    }

    public String error()
    {
        return error;
    }

    public static ApiResult valid(Response response, String string)
    {
        return new ApiResult(true, response, string, "");
    }

    public static ApiResult error(Response response, String string, String error)
    {
        return new ApiResult(false, response, string, error);
    }
}