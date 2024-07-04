package com.axelor.currency.db.repo;

import com.axelor.currency.db.Currency;
import com.google.inject.Inject;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class CurrencyRateRepository extends CurrencyRepository {
    @Inject
    private EntityManager entityManager;

    public List<Currency> findByDateOfCourseUpdate(LocalDate dateOfCourseUpdate) {
        String jpql = "SELECT c FROM Currency c WHERE c.dateOfCourseUpdate = :date";
        TypedQuery<Currency> query = entityManager.createQuery(jpql, Currency.class);
        query.setParameter("date", dateOfCourseUpdate);
        return query.getResultList();
    }
    public List<Currency> findDailyCurrencies() {
        List<String> codes = List.of("RUB", "EUR", "USD", "KZT");

        String jpql = "SELECT c FROM Currency c " +
                "WHERE c.code IN :codes";
        return entityManager.createQuery(jpql, Currency.class)
                .setParameter("codes", codes)
                .getResultList();
    }
    public List<Currency> findWeeklyCurrencies() {
        List<String> excludeCodes = List.of("RUB", "EUR", "USD", "KZT");

        String jpql = "SELECT c FROM Currency c " +
                "WHERE c.code NOT IN :excludeCodes";
        return entityManager.createQuery(jpql, Currency.class)
                .setParameter("excludeCodes", excludeCodes)
                .getResultList();
    }

}
