package com.mauriciotogneri.apivalidator.kernel;

import com.mauriciotogneri.apivalidator.helpers.StringHelper;

public class TestReport
{
    public final Boolean success;
    public final String clazz;
    public final String method;

    public TestReport(Boolean success)
    {
        this.success = success;

        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        StackTraceElement element = stackTrace[2];

        String className = element.getClassName();
        this.clazz = className.substring(className.lastIndexOf(".") + 1);
        this.method = element.getMethodName();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        else if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        TestReport report = (TestReport) o;

        return (success == report.success) &&
                StringHelper.equals(clazz, report.clazz) &&
                StringHelper.equals(method, report.method);
    }

    @Override
    public int hashCode()
    {
        int result = (success ? 1 : 0);

        result = 31 * result + clazz.hashCode();
        result = 31 * result + method.hashCode();

        return result;
    }
}