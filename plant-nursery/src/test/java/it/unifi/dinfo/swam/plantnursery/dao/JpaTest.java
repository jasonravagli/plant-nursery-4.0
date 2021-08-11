package it.unifi.dinfo.swam.plantnursery.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class JpaTest {

	private static EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	@BeforeAll
	public static void setUpClass() {
		System.out.println("Creating EntityManagerFactory");
		entityManagerFactory = Persistence.createEntityManagerFactory("test");
	}

	@BeforeEach
	public void setUp() throws IllegalAccessException {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		init();
		entityManager.getTransaction().commit();
		entityManager.clear();

		entityManager.getTransaction().begin();
	}

	@AfterEach
	public void tearDown() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		entityManager.close();
	}

	@AfterAll
	public static void tearDownClass() {
		entityManagerFactory.close();
	}

	protected abstract void init() throws IllegalAccessException;
}
