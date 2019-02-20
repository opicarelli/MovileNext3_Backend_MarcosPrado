package com.opicarelli.movilenext3.ejb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.entity.Product;
import com.opicarelli.movilenext3.ejb.entity.Worker;
import com.opicarelli.movilenext3.ejb.extension.entity.ProductCategoryTemperature;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

public class JPAUnitTest {

	protected static Logger LOGGER = Logger.getLogger(JPAUnitTest.class);
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

	public Worker createWorker(String cpf) {
		Worker worker = new Worker(cpf);
		em.getTransaction().begin();
		em.persist(worker);
		em.getTransaction().commit();
		return worker;
	}

	public Region createRegion(String polygonWkt) {
		Region region = new Region(polygonWkt);
		em.getTransaction().begin();
		em.persist(region);
		em.getTransaction().commit();
		return region;
	}
}