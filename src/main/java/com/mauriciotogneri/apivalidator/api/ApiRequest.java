package com.mauriciotogneri.apivalidator.api;

import com.mauriciotogneri.apivalidator.parameters.body.BodyParameter;
import com.mauriciotogneri.apivalidator.parameters.form.FormParameters;
import com.mauriciotogneri.apivalidator.parameters.header.HeaderParameters;
import com.mauriciotogneri.apivalidator.parameters.path.PathParameters;
import com.mauriciotogneri.apivalidator.parameters.url.UrlParameters;
import com.mauriciotogneri.apivalidator.responses.EmptyExpectedResponse;
import com.mauriciotogneri.apivalidator.responses.ExpectedResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class ApiRequest
{
    private final OkHttpClient client;
    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final RequestBody body;
    private final ExpectedResponse expectedResponse;
    private Request request;

    private ApiRequest(OkHttpClient client, String method, String url, Map<String, String> headers, RequestBody body, ExpectedResponse expectedResponse)
    {
        this.client = client;
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.expectedResponse = expectedResponse;
    }

    public Integer code()
    {
        return expectedResponse.code();
    }

    public Request request()
    {
        return request;
    }

    public ApiResult execute()
    {
        try
        {
            Response response = response();

            return expectedResponse.check(response);
        }
        catch (Exception e)
        {
            return ApiResult.error(null, "", e.getLocalizedMessage());
        }
    }

    private Response response() throws IOException
    {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.method(method, body);

        headers.forEach(builder::addHeader);
        request = builder.build();

        return client.newCall(request).execute();
    }

    public static class Builder
    {
        private final OkHttpClient client;
        private final String method;
        private final StringBuilder url;
        private final Map<String, String> headers;
        private RequestBody body;
        private ExpectedResponse response;

        public Builder(OkHttpClient client, String method, String url)
        {
            this.client = client;
            this.method = method;
            this.url = new StringBuilder(url);
            this.headers = new HashMap<>();
            this.response = new EmptyExpectedResponse(200);
        }

        public void url(UrlParameters urlParameters)
        {
            url(urlParameters.toString());
        }

        public void url(String parameters)
        {
            url.append(parameters);
        }

        public void path(PathParameters pathParameters)
        {
            for (Entry<String, String> entry : pathParameters)
            {
                path(entry.getKey(), entry.getValue());
            }
        }

        public void path(PathParameters pathParameters, String wrapper)
        {
            for (Entry<String, String> header : pathParameters)
            {
                path(String.format(wrapper, header.getKey()), header.getValue());
            }
        }

        public void path(String name, Object value)
        {
            int index = url.indexOf(name);

            if (index <= 0)
            {
                throw new RuntimeException(String.format("Invalid parameter name: %s", name));
            }

            url.replace(index, index + name.length(), value.toString());
        }

        public void header(HeaderParameters headerParameters)
        {
            for (Entry<String, String> header : headerParameters)
            {
                header(header.getKey(), header.getValue());
            }
        }

        public void header(Header header)
        {
            header(header.name.string(Charset.forName("UTF-8")), header.value.string(Charset.forName("UTF-8")));
        }

        public void header(String key, Object value)
        {
            headers.put(key, value.toString());
        }

        public void form(FormParameters formParameters)
        {
            FormBody.Builder builder = new FormBody.Builder();

            for (Entry<String, String> entry : formParameters)
            {
                builder.add(entry.getKey(), entry.getValue());
            }

            this.body = builder.build();
        }

        public void body(BodyParameter bodyParameter)
        {
            body(bodyParameter.body());
        }

        public void body(RequestBody requestBody)
        {
            this.body = requestBody;
        }

        public void response(ExpectedResponse expectedResponse)
        {
            response = expectedResponse;
        }

        public ApiRequest build()
        {
            return new ApiRequest(client, method, url.toString(), headers, body, response);
        }
    }
}