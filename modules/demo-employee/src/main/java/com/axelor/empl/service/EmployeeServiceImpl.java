package com.axelor.empl.service;

import com.axelor.empl.db.repo.EmplRepository;
import com.axelor.employee.db.Employee;
import com.axelor.employee.db.Status2;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class EmployeeServiceImpl implements EmployeeService{
    @Inject
  private EmplRepository emplRepository;

    @Transactional
    @Override
    public void updateEmployee(Employee employee) {
        Employee oldEmployee = emplRepository.find(employee.getId());
        if (oldEmployee != null) {
            Employee newEmployee = new Employee();
            newEmployee.setFirstName(employee.getFirstName());
            newEmployee.setLastName(employee.getLastName());
            newEmployee.setAge(employee.getAge());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setCompany(employee.getCompany());
            newEmployee.setStatus(Status2.ACTIVE);

            emplRepository.save(newEmployee);

            oldEmployee.setStatus(Status2.INACTIVE);
            emplRepository.save(oldEmployee);
        } else {
            throw new RuntimeException("Запись не найдена");
        }
    }
}
