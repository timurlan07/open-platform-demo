package com.axelor.empl;

import com.axelor.app.AxelorModule;
import com.axelor.company.service.CompaniesService;
import com.axelor.company.service.CompaniesServiceImpl;
import com.axelor.empl.service.EmployeeService;
import com.axelor.empl.service.EmployeeServiceImpl;

public class EmployeeModule extends AxelorModule {
    @Override
    protected void configure() {
        bind(EmployeeService.class).to(EmployeeServiceImpl.class);

    }
}
