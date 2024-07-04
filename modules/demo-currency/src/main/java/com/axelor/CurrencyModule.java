package com.axelor;

import com.axelor.app.AxelorModule;
import com.axelor.currency.service.CurrencyService;
import com.axelor.currency.service.CurrencyServiceImpl;

public class CurrencyModule extends AxelorModule {
    @Override
    protected void configure() {
        bind(CurrencyService.class).to(CurrencyServiceImpl.class);
    }
}
