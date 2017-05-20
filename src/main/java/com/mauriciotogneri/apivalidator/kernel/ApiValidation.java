package com.mauriciotogneri.apivalidator.kernel;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiValidation
{
    private final List<ValidationModule> modules = new ArrayList<>();

    protected void add(ValidationModule validationModule)
    {
        modules.add(validationModule);
    }

    protected void start(Logger logger) throws Exception
    {
        DateTime startTime = new DateTime();

        List<TestReport> reports = new ArrayList<>();

        for (ValidationModule module : modules)
        {
            reports.addAll(module.run(logger));
        }

        printSummary(reports, startTime, logger);
    }

    private void printSummary(List<TestReport> reports, DateTime startTime, Logger logger)
    {
        Boolean allTestPassed = reports.stream().allMatch(r -> r.success);

        if (allTestPassed)
        {
            logger.log("%nAPI VALIDATION FINISHED SUCCESSFULLY");
        }
        else
        {
            List<TestReport> errorReports = reports.stream().filter(r -> !r.success).collect(Collectors.toList());

            logger.error("%nERRORS FOUND: %d", errorReports.size());

            for (TestReport report : errorReports)
            {
                logger.error("%s: %s", report.clazz, report.method);
            }
        }

        DateTime endTime = new DateTime();

        Duration duration = new Duration(startTime, endTime);

        PeriodFormatterBuilder formatterBuilder = new PeriodFormatterBuilder();
        formatterBuilder.appendMinutes();
        formatterBuilder.appendSuffix(" minutes ");
        formatterBuilder.appendSeconds();
        formatterBuilder.appendSuffix(" seconds");

        String formatted = formatterBuilder.toFormatter().print(duration.toPeriod());

        logger.log("%nTOTAL TIME: %s", formatted);

        if (!allTestPassed)
        {
            System.exit(-1);
        }
    }
}