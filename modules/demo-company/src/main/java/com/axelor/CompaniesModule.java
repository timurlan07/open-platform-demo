package com.axelor;

import com.axelor.app.AxelorModule;
import com.axelor.company.service.CompaniesService;
import com.axelor.company.service.CompaniesServiceImpl;


public class CompaniesModule extends AxelorModule {
    @Override
    protected void configure() {
        bind(CompaniesService.class).to(CompaniesServiceImpl.class);
//        bind(AbstractCompaniesRepository.class).to(ComRepository.class);
    }
}
