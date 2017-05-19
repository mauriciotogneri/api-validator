package com.mauriciotogneri.apivalidator.api;

import com.google.gson.Gson;
import com.mauriciotogneri.apivalidator.helpers.JsonHelper;

import okhttp3.Response;

public class ApiResult
{
    private final boolean valid;
    private final String result;
    private final Response response;
    private final String error;

    private ApiResult(boolean valid, String result, Response response, String error)
    {
        this.valid = valid;
        this.result = result;
        this.response = response;
        this.error = error;
    }

    public boolean isValid()
    {
        return valid;
    }

    public Response response()
    {
        return response;
    }

    public String error()
    {
        return error;
    }

    public String result()
    {
        return result;
    }

    public <T> T result(Class<T> clazz)
    {
        Gson gson = JsonHelper.create(false);

        return gson.fromJson(result, clazz);
    }

    public static ApiResult valid(String result, Response response)
    {
        return new ApiResult(true, result, response, "");
    }

    public static ApiResult error(String result, Response response, String error)
    {
        return new ApiResult(false, result, response, error);
    }
}