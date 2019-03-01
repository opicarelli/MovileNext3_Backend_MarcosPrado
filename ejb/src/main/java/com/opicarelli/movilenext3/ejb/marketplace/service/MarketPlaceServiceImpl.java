package com.opicarelli.movilenext3.ejb.marketplace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.opicarelli.movilenext3.ejb.entity.GenericParameter;
import com.opicarelli.movilenext3.ejb.extension.RegionExtensionRestriction.StandardRegionExtensionRestriction;
import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.geo.service.GeoService;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Product;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

@Stateless
public class MarketPlaceServiceImpl implements MarketPlaceService {

	@PersistenceContext(unitName = "MovileNext3DS")
	private EntityManager em;

	@EJB
	private GeoService geoService;

	@Override
	public List<Region> findAllRegion() {
		return em.createQuery("from Region", Region.class).getResultList();
	}

	@Override
	public List<Establishment> findAllEstablishment(Double coordinateX, Double coordinateY) {
		List<Establishment> result = new ArrayList<>();
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
				"select ext from RegionExtension ext JOIN ext.regions r where r.id = :idRegion", RegionExtension.class);
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
	public List<Product> findAllProduct(Double coordinateX, Double coordinateY) {
		List<Product> result = new ArrayList<>();

		// Get Region
		Region region = geoService.findRegion(coordinateX, coordinateY);
		if (region != null) {

			List<RegionExtension> extensions = new ArrayList<>();
			List<Region> regions = new ArrayList<>();

			// Get extensions
			if (this.isFlagRegionExtensionEnabled()) {
				extensions = this.findAllRegionExtension(region);
				regions = extensions.stream().flatMap(item -> item.getRegions().stream()).collect(Collectors.toList());
			}
			if (regions.isEmpty()) {
				regions = Arrays.asList(region);
			}

			// Get establishments
			List<Establishment> establishments = this.findAllEstablishment(regions);

			// Get products
			for (Establishment establishment : establishments) {

				// All
				if (establishment.getRegion().equals(region)) {
					result.addAll(establishment.getProducts());

				} else {

					// By Extension restrictions
					Optional<RegionExtension> extension = extensions.stream()
							.filter(item -> item.getRegions().contains(establishment.getRegion())).findFirst();
					if (extension.isPresent()) {
						StandardRegionExtensionRestriction restriction = StandardRegionExtensionRestriction.PRODUCT_TEMPERATURE;
						for (Product product : establishment.getProducts()) {
							boolean isFeasible = restriction.isFeasible(extension.get(), product); // TODO List of
																									// restrictions
							if (isFeasible) {
								result.add(product);
							}
						}
					}

				}

			}
		}
		return result;
	}

	@Override
	public boolean isFlagRegionExtensionEnabled() {
		try {
			GenericParameter genericParameter = getGenericParameterFlagRegionExtension("FLAG_REGION_EXTENSION_ENABLED");
			if (genericParameter == null) {
				return false;
			}
			// XXX As long as possible migrate to a generic construct by class type
			return Boolean.valueOf(genericParameter.getValueParameter());
		} catch (NonUniqueResultException e) {
			return false;
		}
	}

	@Override
	public void flagRegionExtension(boolean enable) {
		GenericParameter genericParameter = getGenericParameterFlagRegionExtension("FLAG_REGION_EXTENSION_ENABLED");
		if (genericParameter != null) {
			genericParameter.setValueParameter(String.valueOf(enable));
		}
	}

	private GenericParameter getGenericParameterFlagRegionExtension(String keyParameter) {
		String jpql = "from GenericParameter where keyParameter = :keyParameter";
		TypedQuery<GenericParameter> query = em.createQuery(jpql, GenericParameter.class);
		query.setParameter("keyParameter", keyParameter);
		GenericParameter genericParameter = query.getSingleResult();
		return genericParameter;
	}
}
