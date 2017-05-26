package com.mauriciotogneri.apivalidator.parameters.body;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class InputStreamBodyParameter implements BodyParameter
{
    private final MediaType contentType;
    private final InputStream inputStream;

    public InputStreamBodyParameter(MediaType contentType, InputStream inputStream)
    {
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public InputStreamBodyParameter(String contentType, InputStream inputStream)
    {
        this(MediaType.parse(contentType), inputStream);
    }

    @Override
    public RequestBody body()
    {
        return new RequestBody()
        {
            @Override
            public MediaType contentType()
            {
                return contentType;
            }

            @Override
            public long contentLength() throws IOException
            {
                return inputStream.available();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException
            {
                Source source = null;

                try
                {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                }
                finally
                {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}