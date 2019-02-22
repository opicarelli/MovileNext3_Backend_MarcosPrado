package com.opicarelli.movilenext3.ejb.marketplace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.geo.service.GeoService;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

@Stateless
public class MarketPlaceServiceImpl implements MarketPlaceService {

	@PersistenceContext(unitName = "MovileNext3DS")
	private EntityManager em;

	@EJB
	private GeoService geoService;

	@Override
	public List<Establishment> findAllEstablishment(Double coordinateX, Double coordinateY) {
		List<Establishment> result = new ArrayList<Establishment>();
		Region region = geoService.findRegion(coordinateX, coordinateY);
		if (region != null) {
			List<Region> regions = new ArrayList<>();
			if (this.isFlagRegionExtensionEnabled()) {
				List<RegionExtension> extensions = this.findAllRegionExtension(region);
				regions = extensions.stream().flatMap(item -> item.getRegions().stream()).collect(Collectors.toList());
			}
			if (regions.isEmpty()) {
				regions = Arrays.asList(region);
			}
			result = this.findAllEstablishment(regions);
		}
		return result;
	}

	@Override
	public List<RegionExtension> findAllRegionExtension(Region region) {
		TypedQuery<RegionExtension> query = em.createQuery(
				"from RegionExtension ext JOIN FETCH r.regions r where r.id = :idRegion", RegionExtension.class);
		query.setParameter("idRegion", region.getId());
		return query.getResultList();
	}

	@Override
	public List<Establishment> findAllEstablishment(List<Region> regions) {
		StringBuilder jpql = new StringBuilder("from Establishment where 1 = 1");
		Map<String, Object> parameters = new HashMap<>();
		if (regions != null && !regions.isEmpty()) {
			List<Long> regionsIds = regions.stream().map(item -> item.getId()).collect(Collectors.toList());
			jpql.append(" AND region.id IN (:regionsIds)");
			parameters.put("regionsIds", regionsIds);
		}
		TypedQuery<Establishment> query = em.createQuery(jpql.toString(), Establishment.class);
		for (Entry<String, Object> param : parameters.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		List<Establishment> result = query.getResultList();
		return result;
	}

	@Override
	public boolean isFlagRegionExtensionEnabled() {
		// TODO Create system param
		return true;
	}
}
