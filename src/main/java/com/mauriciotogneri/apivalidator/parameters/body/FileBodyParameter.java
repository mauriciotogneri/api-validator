package com.mauriciotogneri.apivalidator.parameters.body;

import java.io.File;

import okhttp3.RequestBody;

public class FileBodyParameter extends BodyParameter
{
    private final File body;

    public FileBodyParameter(File body)
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