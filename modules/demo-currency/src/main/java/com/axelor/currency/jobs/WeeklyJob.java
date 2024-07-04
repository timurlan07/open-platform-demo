package com.axelor.currency.jobs;

import com.axelor.currency.service.CurrencyService;
import com.google.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WeeklyJob implements Job {
    @Inject
    private CurrencyService currencyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        currencyService.fetchAndSaveCurrencyRates("https://www.nbkr.kg/XML/weekly.xml");
        System.out.println("Weekly working");
    }

}
