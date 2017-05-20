package com.mauriciotogneri.apivalidator.parameters.body;

import com.mauriciotogneri.apivalidator.helpers.JsonHelper;

import okhttp3.RequestBody;

public class JsonBodyParameter extends BodyParameter
{
    private final Object body;

    public JsonBodyParameter(Object body)
    {
        super("application/json");

        this.body = body;
    }

    @Override
    public RequestBody body()
    {
        return RequestBody.create(contentType(), JsonHelper.json(body));
    }
}