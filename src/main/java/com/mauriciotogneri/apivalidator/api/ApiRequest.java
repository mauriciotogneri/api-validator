package com.mauriciotogneri.apivalidator.api;

import com.mauriciotogneri.apivalidator.kernel.responses.EmptyExpectedResponse;
import com.mauriciotogneri.apivalidator.kernel.responses.ExpectedResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRequest
{
    private final OkHttpClient client;
    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final RequestBody body;
    private final ExpectedResponse expectedResponse;
    private Request request;

    private ApiRequest(OkHttpClient client, String url, String method, Map<String, String> headers, RequestBody body, ExpectedResponse expectedResponse)
    {
        this.client = client;
        this.url = url;
        this.expectedResponse = expectedResponse;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public String code()
    {
        return expectedResponse.code();
    }

    private Response response() throws IOException
    {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.method(method, body);

        /*if (body != null)
        {
            builder.addHeader("Content-Type", body.contentType().toString());
        }*/

        headers.forEach(builder::addHeader);

        request = builder.build();

        return client.newCall(request).execute();
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
            return ApiResult.error("", null, e.getLocalizedMessage());
        }
    }

    public static class Builder
    {
        private final OkHttpClient client;
        private final StringBuilder url;
        private final String method;
        private final Map<String, String> headers;
        private ExpectedResponse response;
        private RequestBody parameters;

        public Builder(OkHttpClient client, String url, String method)
        {
            this.client = client;
            this.url = new StringBuilder(url);
            this.method = method;
            this.response = new EmptyExpectedResponse(200);
            this.headers = new HashMap<>();
        }

        public void bodyParameters(RequestBody parameters)
        {
            this.parameters = parameters;
        }

        // TODO
        /*public void urlParameters(UrlParameters body)
        {
            url.append(body.get(url.toString().contains("?")));
        }*/

        public void pathParameter(String name, Object value)
        {
            int index = url.indexOf(name);

            if (index <= 0)
            {
                throw new RuntimeException(String.format("Invalid parameter name: %s", name));
            }

            url.replace(index, index + name.length(), value.toString());
        }

        public void header(String key, Object value)
        {
            headers.put(key, value.toString());
        }

        public void response(ExpectedResponse expectedResponse)
        {
            response = expectedResponse;
        }

        public ApiRequest build()
        {
            return new ApiRequest(client, url.toString(), method, headers, parameters, response);
        }
    }
}