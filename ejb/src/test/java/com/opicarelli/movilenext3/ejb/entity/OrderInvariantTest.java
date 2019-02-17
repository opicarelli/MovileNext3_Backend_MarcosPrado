package com.opicarelli.movilenext3.ejb.entity;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.JPAUnitTest;
import com.opicarelli.movilenext3.ejb.extension.entity.ProductCategoryTemperature;

public class OrderInvariantTest extends JPAUnitTest {

	@Test
	public void testCreateOrderSuccess() {

		try {
			// Setup
			Product product = createProduct("açaí", ProductCategoryTemperature.FROZEN);

			// Create Order
			OrderItem item1 = new OrderItem(product);
			Locality origin = new Locality(1.0, 1.0);
			Locality destination = new Locality(5.0, 5.0);
			Order order = new Order(origin, destination, Arrays.asList(item1));

			// Test JPA
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();

			Assert.assertNotNull(em.find(Order.class, order.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateOrderInvariantNullOrigin() {

		try {
			// Create Order
			Locality origin = null;
			Locality destination = new Locality(5.0, 5.0);
			Order order = new Order(origin, destination, Collections.emptyList());

			// Test JPA
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();

			Assert.fail();
		} catch (NullPointerException e) {
			// SUCCESS
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateOrderInvariantNullDestination() {

		try {
			// Create Order
			Locality origin = new Locality(1.0, 1.0);
			Locality destination = null;
			Order order = new Order(origin, destination, Collections.emptyList());

			// Test JPA
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();

			Assert.fail();
		} catch (NullPointerException e) {
			// SUCCESS
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateOrderInvariantEmptyItems() {

		try {
			// Create Order
			Locality origin = new Locality(1.0, 1.0);
			Locality destination = new Locality(5.0, 5.0);
			Order order = new Order(origin, destination, Collections.emptyList());

			// Test JPA
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();

			Assert.fail();
		} catch (IllegalArgumentException e) {
			// SUCCESS
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateOrderInvariantEqualsOriginAndDestination() {

		try {
			// Setup
			Product product = createProduct("açaí", ProductCategoryTemperature.FROZEN);

			// Create Order
			OrderItem item1 = new OrderItem(product);
			Locality origin = new Locality(1.0, 1.0);
			Locality destination = new Locality(1.0, 1.0);
			Order order = new Order(origin, destination, Arrays.asList(item1));

			// Test JPA
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();

			Assert.fail();
		} catch (IllegalArgumentException e) {
			// SUCCESS
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}
}
