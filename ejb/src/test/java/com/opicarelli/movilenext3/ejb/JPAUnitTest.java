package com.opicarelli.movilenext3.ejb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.entity.Locality;
import com.opicarelli.movilenext3.ejb.entity.Worker;
import com.opicarelli.movilenext3.ejb.extension.entity.CategoryTemperature;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Product;
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

	protected Product getSimpleProductAcai() {
		String polygonWkt1 = "POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))";
		Region region1 = createRegion(polygonWkt1);
		Locality locality = new Locality(-47.08226961230012, -22.825819322651185);
		Establishment establishment = createEstablishment("84.348.151/0001-25", locality, region1);
		Product product = createProduct("açaí", CategoryTemperature.FROZEN, establishment);
		return product;
	}

	protected Establishment createEstablishment(String documentNumber, Locality locality, Region region) {
		Establishment establishment = new Establishment(documentNumber, locality, region);
		em.getTransaction().begin();
		em.persist(establishment);
		em.getTransaction().commit();
		return establishment;
	}

	protected Product createProduct(String name, CategoryTemperature categoryTemperature,
			Establishment establishment) {
		Product product = new Product("açaí", categoryTemperature, establishment);
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
		return product;
	}

	protected Worker createWorker(String cpf) {
		Worker worker = new Worker(cpf);
		em.getTransaction().begin();
		em.persist(worker);
		em.getTransaction().commit();
		return worker;
	}

	protected Region createRegion(String polygonWkt) {
		Region region = new Region(polygonWkt);
		em.getTransaction().begin();
		em.persist(region);
		em.getTransaction().commit();
		return region;
	}
}