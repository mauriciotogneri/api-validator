package com.mauriciotogneri.apivalidator.parameters.url;

import com.mauriciotogneri.apivalidator.parameters.base.ConcatenatedParameters;

public class UrlParameters extends ConcatenatedParameters
{
    public UrlParameters(Object object)
    {
        super(object, true);
    }
}