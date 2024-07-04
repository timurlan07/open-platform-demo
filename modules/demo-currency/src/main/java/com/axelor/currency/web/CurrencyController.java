package com.axelor.currency.web;

import com.axelor.currency.db.Currency;
import com.axelor.currency.db.repo.CurrencyRateRepository;
import com.axelor.currency.service.CurrencyService;
import com.axelor.meta.CallMethod;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

import java.util.List;

@RequestScoped
public class CurrencyController {
    @Inject
    private CurrencyService currencyService;

    @Inject
    private CurrencyRateRepository repository;

    void cron(){

    }
    @CallMethod
    public List<Currency> dailyCurrency(ActionRequest request, ActionResponse response){
        response.setNotify("Currency rates updated successfully!");
        return repository.findDailyCurrencies();

    }
    @CallMethod
    public List<Currency> weeklyCurrency(ActionRequest request, ActionResponse response){
        response.setNotify("Currency rates updated successfully!");
        return repository.findDailyCurrencies();
    }
    @CallMethod()
    public void fetchAndSaveDaily(ActionRequest request, ActionResponse response) {
        String url = "https://www.nbkr.kg/XML/daily.xml";
        System.out.println("Hello Controller version 2");
        currencyService.fetchAndSaveCurrencyRates(url);
        response.setNotify("Currency rates updated successfully!");
        response.setSignal("refresh",null);
    }
    @CallMethod()
    public void fetchAndSaveWeekly(ActionRequest request, ActionResponse response) {
        String url = "https://www.nbkr.kg/XML/weekly.xml";
        System.out.println("Hello Controller version 3");
        currencyService.fetchAndSaveCurrencyRates(url);
        response.setNotify("Currency rates updated successfully!");
    }
}
