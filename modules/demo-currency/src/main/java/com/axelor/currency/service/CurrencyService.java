package com.axelor.currency.service;

import com.axelor.currency.db.Currency;

public interface CurrencyService {

     void fetchAndSaveCurrencyRates(String url);
     void parseAndSave(String xmlContent);


}
