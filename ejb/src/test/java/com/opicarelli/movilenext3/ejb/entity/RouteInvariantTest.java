package com.opicarelli.movilenext3.ejb.entity;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.JPAUnitTest;

public class RouteInvariantTest extends JPAUnitTest {

	@Test
	public void testCreateRouteSuccess() {

		try {
			// Setup
			Worker worker = createWorker("448.951.978-83");

			// Create Route
			// Single Pick - Single Drop
			Leg leg1 = new Leg(new Locality(1.0, 1.0), new Locality(5.0, 5.0));
			Route route = new Route(worker, new LinkedList<Leg>(Arrays.asList(leg1)));

			// Test JPA
			em.getTransaction().begin();
			em.persist(route);
			em.getTransaction().commit();

			Assert.assertNotNull(em.find(Route.class, route.getId()));
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRouteInvariantNullWorker() {

		try {
			// Setup
			Worker worker = null;

			// Create Route
			// Single Pick - Single Drop
			Leg leg1 = new Leg(new Locality(1.0, 1.0), new Locality(5.0, 5.0));
			Route route = new Route(worker, new LinkedList<Leg>(Arrays.asList(leg1)));

			// Test JPA
			em.getTransaction().begin();
			em.persist(route);
			em.getTransaction().commit();

			Assert.fail();
		} catch (NullPointerException e) {
			// SUCCESS
			LOGGER.info(e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRouteInvariantEmptyLegs() {

		try {
			// Setup
			Worker worker = createWorker("448.951.978-83");

			// Create Route
			Route route = new Route(worker, new LinkedList<Leg>());

			// Test JPA
			em.getTransaction().begin();
			em.persist(route);
			em.getTransaction().commit();

			Assert.fail();
		} catch (IllegalArgumentException e) {
			// SUCCESS
			LOGGER.info(e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRouteInvariantFirstStepOrderLegs() {

		try {
			// Setup
			Worker worker = createWorker("448.951.978-83");

			// Create Route
			// Single Pick - Single Drop
			Leg leg1 = new Leg(new Locality(1.0, 1.0), new Locality(5.0, 5.0));
			leg1.setStepOrder(2);
			Route route = new Route(worker, new LinkedList<Leg>(Arrays.asList(leg1)));

			// Test JPA
			em.getTransaction().begin();
			em.persist(route);
			em.getTransaction().commit();

			Assert.fail();
		} catch (IllegalArgumentException e) {
			// SUCCESS
			LOGGER.info(e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRouteInvariantLastStepOrderLegs() {

		try {
			// Setup
			Worker worker = createWorker("448.951.978-83");

			// Create Route
			// Single Pick - Multi Drop
			Leg leg1 = new Leg(new Locality(1.0, 1.0), new Locality(5.0, 5.0));
			Leg leg2 = new Leg(new Locality(5.0, 5.0), new Locality(10.0, 10.0));
			leg1.setStepOrder(1);
			leg2.setStepOrder(3);
			Route route = new Route(worker, new LinkedList<Leg>(Arrays.asList(leg1, leg2)));

			// Test JPA
			em.getTransaction().begin();
			em.persist(route);
			em.getTransaction().commit();

			Assert.fail();
		} catch (IllegalArgumentException e) {
			// SUCCESS
			LOGGER.info(e.getMessage());
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}
}
