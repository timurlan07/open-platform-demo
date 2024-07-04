package com.axelor.currency.jobs;

import com.axelor.currency.service.CurrencyService;
import com.axelor.currency.service.CurrencyServiceImpl;
import com.google.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DailyJob implements Job {
    @Inject
    private CurrencyServiceImpl currencyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        currencyService.fetchAndSaveCurrencyRates("https://www.nbkr.kg/XML/daily.xml");
        System.out.println("Daily job working");
    }


}
