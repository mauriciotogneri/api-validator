package com.mauriciotogneri.apivalidator.parameters.body;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

public class MultipartBodyParameter implements BodyParameter
{
    private final MultipartBody.Builder builder;

    public MultipartBodyParameter(MediaType mediaType)
    {
        this.builder = new MultipartBody.Builder();
        this.builder.setType(mediaType);
    }

    public void addPart(Part part)
    {
        builder.addPart(part);
    }

    public void addPart(RequestBody body)
    {
        builder.addPart(body);
    }

    public void addPart(Headers headers, RequestBody body)
    {
        builder.addPart(headers, body);
    }

    public void addFormPart(String name, String value)
    {
        builder.addFormDataPart(name, value);
    }

    public void addFormPart(String name, String value, RequestBody body)
    {
        builder.addFormDataPart(name, value, body);
    }

    @Override
    public RequestBody body()
    {
        return builder.build();
    }
}