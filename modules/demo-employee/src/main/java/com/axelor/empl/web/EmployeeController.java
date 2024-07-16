package com.axelor.empl.web;

import com.axelor.company.db.Companies;
import com.axelor.company.service.CompaniesServiceImpl;
import com.axelor.empl.service.EmployeeServiceImpl;
import com.axelor.employee.db.Employee;
import com.axelor.meta.CallMethod;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import org.jboss.resteasy.plugins.guice.RequestScoped;

@RequestScoped
public class EmployeeController {
    @Inject
    private EmployeeServiceImpl employeeService;

    @CallMethod
    public void updateEmployee(ActionRequest request, ActionResponse response) {
        System.out.println("Update Employee");
        Employee employee = request.getContext().asType(Employee.class);
        try {
            employeeService.updateEmployee(employee);
            response.setInfo("Данные успешно обновлены");
        } catch (RuntimeException e) {
            response.setError(e.getMessage());
        }
    }
}
