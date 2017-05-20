package com.mauriciotogneri.apivalidator.parameters.body;

import okhttp3.RequestBody;

public class BinaryBodyParameter extends BodyParameter
{
    private final byte[] body;

    public BinaryBodyParameter(byte[] body)
    {
        super("application/json");

        this.body = body;
    }

    @Override
    public RequestBody body()
    {
        return RequestBody.create(contentType(), body);
    }
}