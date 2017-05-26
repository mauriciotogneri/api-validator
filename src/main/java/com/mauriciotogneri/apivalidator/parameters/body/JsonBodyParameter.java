package com.mauriciotogneri.apivalidator.parameters.body;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JsonBodyParameter implements BodyParameter
{
    private final Object body;
    private final Gson gson;

    public JsonBodyParameter(Object body, Gson gson)
    {
        this.body = body;
        this.gson = gson;
    }

    public JsonBodyParameter(Object body)
    {
        this(body, new Gson());
    }

    @Override
    public RequestBody body()
    {
        return RequestBody.create(MediaType.parse("application/json"), gson.toJson(body));
    }
}