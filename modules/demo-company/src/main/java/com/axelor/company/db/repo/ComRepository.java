package com.axelor.company.db.repo;

import com.axelor.company.db.Companies;
import com.axelor.company.db.Status;
import com.axelor.db.JPA;
import com.google.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ComRepository extends CompaniesRepository {
    @Inject
    private EntityManager em;


    //    @Override
//    public Companies save(Companies entity) {
//        System.out.println("Method save");
//        System.out.println(entity);
//        if (entity.getVersion() != 0) {
//            Companies existingEntity = getCompany(entity.getId());
//            System.out.println(existingEntity);
//            System.out.println(entity);
//            if (existingEntity == null) {
//                throw new RuntimeException("Сущность с id=" + entity.getId() + " не найдена");
//            }
//
//            System.out.println("Updating existing entity");
//            return updateExistingCompany(existingEntity, entity);
//        }
//        return entity;
//    }

    private Companies updateExistingCompany(Companies existingEntity, Companies newEntityData) {
        existingEntity.setStatus(Status.INACTIVE);
        JPA.persist(existingEntity);
        System.out.println("Existing entity deactivated");

        Companies newEntity = createNewCompany(newEntityData);
        System.out.println("New entity created: " + newEntity);

        updateEmployeeCompany(newEntity.getId(), existingEntity.getId());
        System.out.println("Employee company updated");

        return newEntity;
    }

    private Companies createNewCompany(Companies newEntityData) {
        Companies newEntity = new Companies();
        newEntity.setName(newEntityData.getName());
        newEntity.setLocation(newEntityData.getLocation());
        newEntity.setDateOfCreation(newEntityData.getDateOfCreation());
        newEntity.setStatus(Status.ACTIVE);
        JPA.save(newEntity);
        return newEntity;
    }

    public void updateCompanyStatus(Long companyId, Status newStatus) {
        em.createQuery("UPDATE Companies c SET c.status = :newStatus WHERE c.id = :companyId")
                .setParameter("newStatus", newStatus)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }


    public void updateEmployeeCompany(Long newCompanyId, Long oldCompanyId) {
        String jpql = "UPDATE Employee e SET e.company.id = :newCompanyId WHERE e.company.id = :oldCompanyId";
        Query query = em.createQuery(jpql);
        query.setParameter("newCompanyId", newCompanyId);
        query.setParameter("oldCompanyId", oldCompanyId);
        int updatedCount = query.executeUpdate();
        System.out.println("Количество обновленных записей: " + updatedCount);
    }

    public Companies getCompany(Long id) {
        return em.createQuery("SELECT c FROM Companies c WHERE c.id=:id", Companies.class)
                .setParameter("id", id).getSingleResult();
    }


}
