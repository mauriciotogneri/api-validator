package com.mauriciotogneri.apivalidator.parameters.form;

import com.mauriciotogneri.apivalidator.parameters.base.ConcatenatedParameters;

public class FormParameters extends ConcatenatedParameters
{
    public FormParameters(Object object)
    {
        super(object, false);
    }
}