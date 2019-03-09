package com.mauriciotogneri.apivalidator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EndPointExecutor<T> implements InvocationHandler
{
    private final Class<T> clazz;

    private EndPointExecutor(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public static void main(String[] args)
    {
        //MagicEndPoint endpoint = EndPointExecutor.of(CreateMatch.class);
        //System.out.println(endpoint.execute());
    }

    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> clazz)
    {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, new EndPointExecutor<>(clazz));
    }

    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] methodParameters)
    {
        /*EndPoint endPoint = clazz.getAnnotation(EndPoint.class);
        Parameters parameters = clazz.getAnnotation(Parameters.class);
        Responses responses = clazz.getAnnotation(Responses.class);

        builder.header(null);
        builder.body(null);
        builder.form(null);
        builder.path(null);
        builder.response();

        ApiRequest.Builder builder = new ApiRequest.Builder(new OkHttpClient(), endPoint.method(), endPoint.path());
        builder.header(session(session));
        builder.body(new JsonBodyParameter(data));
        builder.response(jsonResponse());

        return builder.build().execute();*/

        return null;
    }
}