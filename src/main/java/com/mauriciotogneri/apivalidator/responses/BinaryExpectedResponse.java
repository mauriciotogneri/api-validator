package com.mauriciotogneri.apivalidator.responses;

import com.mauriciotogneri.apivalidator.api.ApiResult;

import okhttp3.Response;

public class BinaryExpectedResponse extends ExpectedResponse
{
    public BinaryExpectedResponse(Integer code)
    {
        super(code);
    }

    @Override
    public ApiResult validate(Response response, String body) throws Exception
    {
        return ApiResult.valid(body, response);
    }
}