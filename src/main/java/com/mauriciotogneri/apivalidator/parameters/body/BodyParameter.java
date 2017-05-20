package com.mauriciotogneri.apivalidator.parameters.body;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class BodyParameter
{
    private final String contentType;

    public BodyParameter(String contentType)
    {
        this.contentType = contentType;
    }

    protected MediaType contentType()
    {
        return MediaType.parse(contentType);
    }

    public abstract RequestBody body();
}