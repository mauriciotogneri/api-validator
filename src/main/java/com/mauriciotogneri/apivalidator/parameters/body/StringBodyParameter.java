package com.mauriciotogneri.apivalidator.parameters.body;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StringBodyParameter implements BodyParameter
{
    private final MediaType mediaType;
    private final String body;

    public StringBodyParameter(MediaType mediaType, String body)
    {
        this.mediaType = mediaType;
        this.body = body;
    }

    public StringBodyParameter(String mediaType, String body)
    {
        this.mediaType = MediaType.parse(mediaType);
        this.body = body;
    }

    @Override
    public RequestBody body()
    {
        return RequestBody.create(mediaType, body);
    }
}