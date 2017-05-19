package com.mauriciotogneri.apivalidator.kernel;

import java.util.ArrayList;
import java.util.List;

public class ValidationService
{
    private final List<ValidationEndPoint> endpoints = new ArrayList<>();

    public void addEndPoint(ValidationEndPoint validationEndPoint)
    {
        endpoints.add(validationEndPoint);
    }

    public List<TestReport> run(Logger logger) throws Exception
    {
        List<TestReport> reports = new ArrayList<>();

        if (!endpoints.isEmpty())
        {
            logger.log("%n%s:", getClass().getSimpleName());
        }

        for (ValidationEndPoint endpoint : endpoints)
        {
            reports.addAll(endpoint.check());
        }

        return reports;
    }
}