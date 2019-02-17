package com.opicarelli.movilenext3.ejb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.entity.Product;
import com.opicarelli.movilenext3.ejb.extension.entity.ProductCategoryTemperature;

public class JPAUnitTest {

	protected static EntityManager em;
	private static EntityManagerFactory emf;

	@Before
	public void before() {
		emf = Persistence.createEntityManagerFactory("MovileNext3DS");
		em = emf.createEntityManager();
	}

	@After
	public void after() {
		em.close();
		emf.close();
	}

	@Test
	public void createEMTest() {
		Assert.assertNotNull(em);
	}
	
	public Product createProduct(String name, ProductCategoryTemperature categoryTemperature) {
		Product product = new Product("açaí", ProductCategoryTemperature.FROZEN);
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
		return product;
	}
}