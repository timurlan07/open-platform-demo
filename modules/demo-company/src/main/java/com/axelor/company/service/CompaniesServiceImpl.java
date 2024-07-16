package com.axelor.company.service;

import com.axelor.company.db.Companies;
import com.axelor.company.db.Status;
import com.axelor.company.db.repo.ComRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import java.util.Objects;

public class CompaniesServiceImpl implements CompaniesService {
    @Inject
    private ComRepository companiesRepository;

    @Override
    @Transactional
    public void updateCompany(Companies newCompanyData) {
        Companies oldCompany = companiesRepository.find(newCompanyData.getId());
        System.out.println(newCompanyData);
        System.out.println(oldCompany);
        if (oldCompany == null) {
            throw new RuntimeException("Не найдено !!!");
        }
        if (hasChanges(oldCompany, newCompanyData)) {
            Companies newCompany = createNewCompany(newCompanyData);
            companiesRepository.save(newCompany);
            changeStatusOldCompany(oldCompany);
            updateEmployeeCompany(newCompany.getId(), oldCompany.getId());
        } else {
            System.out.println("Нет изменений для сохранения");
            throw new RuntimeException("Нет изменений для сохранения");
        }
    }

    private boolean hasChanges(Companies oldCompany, Companies newCompanyData) {
        return !Objects.equals(oldCompany.getName(), newCompanyData.getName()) ||
                !Objects.equals(oldCompany.getLocation(), newCompanyData.getLocation()) ||
                !Objects.equals(oldCompany.getDateOfCreation(), newCompanyData.getDateOfCreation());
    }

    private void changeStatusOldCompany(Companies oldCompany) {
//      companiesRepository.updateCompanyStatus(oldCompany.getId(),Status.INACTIVE);
        oldCompany.setStatus(Status.INACTIVE);
        companiesRepository.save(oldCompany);
    }

    private Companies createNewCompany(Companies newCompanyData) {
        Companies newCompany = new Companies();
        newCompany.setName(newCompanyData.getName());
        newCompany.setLocation(newCompanyData.getLocation());
        newCompany.setDateOfCreation(newCompanyData.getDateOfCreation());
        newCompany.setStatus(Status.ACTIVE);
//      companiesRepository.save(newCompany);
        System.out.println("Создана новая компания: " + newCompany);
        return newCompany;
    }

    private void updateEmployeeCompany(Long newCompanyId, Long oldCompanyId) {
        companiesRepository.updateEmployeeCompany(newCompanyId, oldCompanyId);
        System.out.println("Обновлены компании сотрудников");
    }
}