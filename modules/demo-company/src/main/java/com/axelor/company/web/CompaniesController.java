package com.axelor.company.web;

import com.axelor.company.db.Companies;
import com.axelor.company.db.repo.ComRepository;

import com.axelor.company.service.CompaniesServiceImpl;
import com.axelor.db.JPA;
import com.axelor.meta.CallMethod;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class CompaniesController {

    @Inject
    private CompaniesServiceImpl companiesService;
    @Inject
    private ComRepository companiesRepository;

    @CallMethod
    public void updateCompany(ActionRequest request, ActionResponse response) {
        if (request.getContext().get("id") == null) {
            JPA.edit(Companies.class, request.getContext());
        } else {
            Companies companies = request.getContext().asType(Companies.class);
            System.out.println(companies);
            companies.setId((Long) request.getContext().get("id"));

            try {
                companiesService.updateCompany(companies);
                response.setInfo("Данные успешно обновлены");
            } catch (RuntimeException e) {
//                    response.setError(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }
}

