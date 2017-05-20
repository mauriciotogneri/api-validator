package com.mauriciotogneri.apivalidator.responses;

import com.mauriciotogneri.apivalidator.api.ApiResult;

import okhttp3.Response;

public class TextExpectedResponse extends ExpectedResponse
{
    public TextExpectedResponse(Integer code)
    {
        super(code);
    }

    @Override
    public ApiResult validate(Response response, String body) throws Exception
    {
        return ApiResult.valid(body, response);
    }
}