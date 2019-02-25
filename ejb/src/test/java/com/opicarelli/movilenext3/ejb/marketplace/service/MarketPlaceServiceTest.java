package com.opicarelli.movilenext3.ejb.marketplace.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.util.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.opicarelli.movilenext3.ejb.entity.Locality;
import com.opicarelli.movilenext3.ejb.extension.entity.CategoryTemperature;
import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.geo.service.GeoService;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

/**
 * This is a mock and a NON EJB unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarketPlaceServiceTest {

	@InjectMocks
	private MarketPlaceService service = new MarketPlaceServiceImpl();

	@Mock
	private GeoService geoService;

	@Mock
	private EntityManager em;

	@Test
	public void testFindAllEstablishmentFlagEnabled() {
		// Regions
		String polygonWkt1 = "POLYGON((-47.1010145892716 -22.81855527197949,-47.092474435768175 -22.828800068388006,-47.08955619235998 -22.83235985574656,-47.09509227176672 -22.838055321834787,-47.10474822422032 -22.83623596274866,-47.10607859989171 -22.83493075537399,-47.10586402317052 -22.822075771583116,-47.1010145892716 -22.81855527197949))";
		String polygonWkt2 = "POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))";
		Region region1 = new Region(polygonWkt1);
		Region region2 = new Region(polygonWkt2);

		// Client coordinate INSIDE REGION 1
		Double clientCoordinateX = -47.09776205157027;
		Double clientCoordinateY = -22.830219698137412;

		// Mock geoService.findRegion
		mockGeoServiceFindRegion();

		// Mock findAllEstablishment
		// Establishment INSIDE REGION 2
		Establishment establishmentRegion2 = mockFindAllEstablishment(region2);

		// Mock findAllRegionExtension
		mockFindAllRegionExtension(region1, region2);

		// Call SERVICE TO TEST
		List<Establishment> establishments = service.findAllEstablishment(clientCoordinateX, clientCoordinateY);

		Assert.isTrue(!establishments.isEmpty());
		Assert.isTrue(establishments.contains(establishmentRegion2));
	}

	private void mockGeoServiceFindRegion() {
		Region mockGeoServiceFindRegion = mock(Region.class);
		when(geoService.findRegion(any(Double.class), any(Double.class))).thenReturn(mockGeoServiceFindRegion);
	}

	@SuppressWarnings("unchecked")
	private Establishment mockFindAllEstablishment(Region region) {
		Locality localityEstablishment = new Locality(-47.08733362292022, -22.82426679667877);
		Establishment establishmentRegion2 = new Establishment("84.348.151/0001-25", localityEstablishment, region);
		TypedQuery<Establishment> mockQueryFindAllEstablishment = mock(TypedQuery.class);
		when(em.createQuery(any(String.class), eq(Establishment.class))).thenReturn(mockQueryFindAllEstablishment);
		when(mockQueryFindAllEstablishment.getResultList()).thenReturn(Arrays.asList(establishmentRegion2));
		return establishmentRegion2;
	}

	@SuppressWarnings("unchecked")
	private void mockFindAllRegionExtension(Region region1, Region region2) {
		Locality localityExtension = new Locality(-47.0812932882187, -22.828232130421583);
		RegionExtension regionExtension = new RegionExtension(localityExtension, Arrays.asList(region1, region2),
				Arrays.asList(CategoryTemperature.FROZEN));
		List<RegionExtension> extensions = Arrays.asList(regionExtension);
		TypedQuery<RegionExtension> mockQueryFindAllRegionExtension = mock(TypedQuery.class);
		when(em.createQuery(any(String.class), eq(RegionExtension.class))).thenReturn(mockQueryFindAllRegionExtension);
		when(mockQueryFindAllRegionExtension.getResultList()).thenReturn(extensions);
	}

}
