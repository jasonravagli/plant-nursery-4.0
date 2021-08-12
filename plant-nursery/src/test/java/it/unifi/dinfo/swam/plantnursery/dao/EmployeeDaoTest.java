package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.Employee;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;

public class EmployeeDaoTest extends JpaTest{
	private EmployeeDao employeeDao;
	
	private Employee employee1;
	private Employee employee2;

	@Override
	protected void init() throws IllegalAccessException {
		employeeDao = new EmployeeDao(this.entityManager);
		
		employee1 = ModelFactory.employee();
		employee1.setEmail("try@try.try");
		employee1.setPassword("psw");
		employee2 = ModelFactory.employee();
		employee2.setEmail("try2@try.try");
		employee2.setPassword("psw2");
				
		this.entityManager.persist(employee1);
		this.entityManager.persist(employee2);
	}
	
	@Test
	public void testSave() {
		Employee employee = ModelFactory.employee();
		employeeDao.save(employee);
		
		TypedQuery<Employee> query = this.entityManager.createQuery("FROM Employee where uuid = :uuid", Employee.class);
		query.setParameter("uuid", employee.getUuid());
		
		try {
			Employee retrievedEmployee = query.getSingleResult();
		}
		catch(NoResultException e) {
			fail();
		}
	}
	
	@Test
	public void findByEmailTest() {
		Employee employee = employeeDao.findByEmail("try@try.try");
		assertEquals(employee.getId(), employee1.getId());
	}
	
	@Test
	public void searchTest() {
		List<Employee> employee = employeeDao.search("try@try.try", 1, 2);
		assertEquals(employee.size(),2);
	}
	
	@Test
	public void getAllTest() {
		List<Employee> employee = employeeDao.getAll();
		
		assertEquals(2, employee.size());
		assertEquals(true, employee.stream().anyMatch(p -> p.equals(employee1)));
		assertEquals(true, employee.stream().anyMatch(p -> p.equals(employee2)));
	}
	
}
