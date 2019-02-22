package com.opicarelli.movilenext3.ejb.geo.service;

import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

public interface GeoService {

	public Region findRegion(Double coordinateX, Double coordinateY);
}