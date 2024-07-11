package com.axelor.contact;

import com.axelor.currency.db.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CurrencyServiceTest {

    @Mock
    private CurrencyRateRepository currencyRateRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseAndSave() throws Exception {
        String xmlContent = "<ValCurs Date=\"04.07.2024\"><Currency ISOCode=\"USD\"><Nominal>1</Nominal><Value>73.50</Value></Currency></ValCurs>";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateOfCourseUpdate = LocalDate.parse("04.07.2024", formatter);

        Currency existingCurrency = new Currency();
        existingCurrency.setCode("USD");
        existingCurrency.setNominal("1");
        existingCurrency.setRate(new BigDecimal("73.50"));
        existingCurrency.setDateOfCourseUpdate(dateOfCourseUpdate);

        when(currencyRateRepository.findByDateOfCourseUpdate(dateOfCourseUpdate)).thenReturn(Collections.emptyList());
        when(currencyRateRepository.findByCode("USD")).thenReturn(null);

        currencyServiceImpl.parseAndSave(xmlContent);

        verify(currencyRateRepository).findByDateOfCourseUpdate(dateOfCourseUpdate);
        verify(currencyRateRepository).findByCode("USD");
        verify(currencyRateRepository).save(any(Currency.class));
    }

    @Test
    public void testParseAndSaveWithExistingData() throws Exception {
        String xmlContent = "<ValCurs Date=\"04.07.2024\"><Currency ISOCode=\"USD\"><Nominal>1</Nominal><Value>73.50</Value></Currency></ValCurs>";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateOfCourseUpdate = LocalDate.parse("04.07.2024", formatter);

        Currency existingCurrency = new Currency();
        existingCurrency.setCode("USD");
        existingCurrency.setNominal("1");
        existingCurrency.setRate(new BigDecimal("73.50"));
        existingCurrency.setDateOfCourseUpdate(dateOfCourseUpdate);

        when(currencyRateRepository.findByDateOfCourseUpdate(dateOfCourseUpdate)).thenReturn(List.of(existingCurrency));

        currencyServiceImpl.parseAndSave(xmlContent);

        verify(currencyRateRepository).findByDateOfCourseUpdate(dateOfCourseUpdate);
        verify(currencyRateRepository, never()).findByCode(anyString());
        verify(currencyRateRepository, never()).save(any(Currency.class));
    }
}