package it.unifi.dinfo.swam.plantnursery.dao;


import it.unifi.dinfo.swam.plantnursery.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class EmployeeDao extends BaseDao<Employee> {

    public EmployeeDao() {
        super(Employee.class);
    }
    
	// Only for testing purposes to inject dependencies
    EmployeeDao(EntityManager entityManager) {
		super(Employee.class, entityManager);
	}

    public Employee findByEmail(String email) {
        try {
            return (Employee) entityManager.createQuery("FROM employees WHERE email=:email").setParameter("email", email).getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    public List<Employee> search(String email, Integer firstResult, Integer maxResults) {
    	email = email.toLowerCase();
        Query q = entityManager.createQuery("FROM employees " +
                "WHERE email=:email")
                .setParameter("email", "%" + email + "%");
        if(firstResult != null)
            q.setFirstResult(firstResult);
        if(maxResults != null)
            q.setMaxResults(maxResults);
        return q.getResultList();
    }

    public List<Employee> getAll() {
        try {
            return entityManager.createQuery("FROM employees").getResultList();
        }
        catch (NoResultException nre){
            return null;
        }
    }
}