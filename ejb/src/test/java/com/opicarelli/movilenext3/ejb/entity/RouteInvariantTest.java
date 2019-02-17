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
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

}
