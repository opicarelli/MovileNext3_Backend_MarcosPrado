package com.opicarelli.movilenext3.ejb.marketplace.service;

import java.util.List;

import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Product;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

public interface MarketPlaceService {

	boolean isFlagRegionExtensionEnabled();

	List<Establishment> findAllEstablishment(Double coordinateX, Double coordinateY);

	List<Establishment> findAllEstablishment(List<Region> regions);

	List<RegionExtension> findAllRegionExtension(Region region);

	List<Product> findAllProduct(Double coordinateX, Double coordinateY);

}