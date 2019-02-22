package com.opicarelli.movilenext3.ejb.geo.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.opicarelli.movilenext3.ejb.geo.GeoUtils;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

/**
 * This class represents a service that find and calculate geometries in memory.
 * This can be replaced for DATABASE calculations. *
 */
@Stateless
public class GeoServiceMemoryCalculationsImpl implements GeoService, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "MovileNext3DS")
	private EntityManager em;

	@Override
	public Region findRegion(Double coordinateX, Double coordinateY) {
		Region result = null;
		TypedQuery<Region> query = em.createQuery("from Region", Region.class);
		List<Region> regions = query.getResultList();
		for (Region region : regions) {
			boolean found = GeoUtils.contains(region.getPolygon(), coordinateX, coordinateY);
			if (found) {
				result = region;
				break;
			}
		}
		return result;
	}

}
