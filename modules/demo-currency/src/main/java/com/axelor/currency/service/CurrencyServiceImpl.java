package com.axelor.currency.service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.axelor.currency.db.Currency;
import com.axelor.currency.db.repo.CurrencyRateRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CurrencyServiceImpl implements CurrencyService {
    @Inject
    private CurrencyRateRepository currencyRateRepository;

    @Override
    public void fetchAndSaveCurrencyRates(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String xmlContent = EntityUtils.toString(entity);
                parseAndSave(xmlContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void parseAndSave(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlContent)));

            Element rootElement = document.getDocumentElement();
            String dateStr = rootElement.getAttribute("Date");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate dateOfCourseUpdate = LocalDate.parse(dateStr, formatter);
            List<Currency> existingCurrencies = currencyRateRepository.findByDateOfCourseUpdate(dateOfCourseUpdate);
            if (!existingCurrencies.isEmpty()) {
                System.out.println("Данные на " + dateStr + " уже существуют. Обновление пропущено.");
                return;
            }
            NodeList nodeList = document.getElementsByTagName("Currency");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element currencyElement = (Element) nodeList.item(i);

                String code = currencyElement.getAttribute("ISOCode");
                String nominal = currencyElement.getElementsByTagName("Nominal").item(0).getTextContent();
                String value = currencyElement.getElementsByTagName("Value").item(0).getTextContent();

                BigDecimal rate = new BigDecimal(value.replace(",", "."));

                Currency existingCurrency = currencyRateRepository.findByCode(code);

                if (existingCurrency == null) {
                    Currency newCurrency = new Currency();
                    newCurrency.setCode(code);
                    newCurrency.setNominal(nominal);
                    newCurrency.setRate(rate);
                    newCurrency.setDateOfCourseUpdate(dateOfCourseUpdate);
                    currencyRateRepository.save(newCurrency);
                    System.out.println("Создана новая валюта: " + newCurrency.getCode());
                } else {
                    existingCurrency.setNominal(nominal);
                    existingCurrency.setRate(rate);
                    existingCurrency.setDateOfCourseUpdate(dateOfCourseUpdate);
                    currencyRateRepository.save(existingCurrency);
                    System.out.println("Обновлена валюта: " + existingCurrency.getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

