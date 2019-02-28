package com.opicarelli.movilenext3.ejb.entity;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.opicarelli.movilenext3.ejb.JPAUnitTest;
import com.opicarelli.movilenext3.ejb.extension.entity.CategoryTemperature;
import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

public class MarketPlaceInvariantTest extends JPAUnitTest {

	@Test
	public void testCreateRegionSuccess() {

		try {
			// Setup
			String polygonWkt = "POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))";

			// Create Region
			Region region = new Region(polygonWkt);
			// Test JPA
			em.getTransaction().begin();
			em.persist(region);
			em.getTransaction().commit();

			Assert.assertNotNull(em.find(Region.class, region.getId()));
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRegionInvariantNullPolygon() {

		try {
			// Setup
			String polygonWkt = null;

			// Create Region
			Region region = new Region(polygonWkt);
			// Test JPA
			em.getTransaction().begin();
			em.persist(region);
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
	public void testCreateEstablishmentSuccess() {

		try {
			// Setup
			String polygonWkt1 = "POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))";
			Region region1 = createRegion(polygonWkt1);

			// Create Establishment
			Locality locality = new Locality(-47.08226961230012, -22.825819322651185);
			Establishment establishment = new Establishment("84.348.151/0001-25", locality, region1);

			// Test JPA
			em.getTransaction().begin();
			em.persist(establishment);
			em.getTransaction().commit();

			Assert.assertNotNull(em.find(Region.class, establishment.getId()));
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCreateRegionExtensionSuccess() {

		try {
			// Setup
			String polygonWkt1 = "POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))";
			String polygonWkt2 = "POLYGON((-47.1010145892716 -22.81855527197949,-47.092474435768175 -22.828800068388006,-47.08955619235998 -22.83235985574656,-47.09509227176672 -22.838055321834787,-47.10474822422032 -22.83623596274866,-47.10607859989171 -22.83493075537399,-47.10586402317052 -22.822075771583116,-47.1010145892716 -22.81855527197949))";
			Region region1 = createRegion(polygonWkt1);
			Region region2 = createRegion(polygonWkt2);

			// Create RegionExtension FROZEN
			Locality locality = new Locality(-47.093354200325074, -22.826387270684734);
			RegionExtension regionExtension = new RegionExtension(locality, Arrays.asList(region1, region2),
					Arrays.asList(CategoryTemperature.FROZEN));

			// Test JPA
			em.getTransaction().begin();
			em.persist(regionExtension);
			em.getTransaction().commit();

			Assert.assertNotNull(em.find(RegionExtension.class, regionExtension.getId()));
		} catch (Exception e) {
			LOGGER.error("Fail", e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			Assert.fail();
		}

	}

}
