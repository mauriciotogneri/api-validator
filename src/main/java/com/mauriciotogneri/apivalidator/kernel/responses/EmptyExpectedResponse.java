package com.mauriciotogneri.apivalidator.kernel.responses;

import com.mauriciotogneri.apivalidator.api.ApiResult;

import okhttp3.Response;

public class EmptyExpectedResponse extends ExpectedResponse
{
    public EmptyExpectedResponse(Integer code)
    {
        super(code);
    }

    @Override
    public ApiResult validate(Response response, String body) throws Exception
    {
        return ApiResult.valid(body, response);
    }

    @Override
    protected Boolean shouldBeEmpty()
    {
        return true;
    }
}