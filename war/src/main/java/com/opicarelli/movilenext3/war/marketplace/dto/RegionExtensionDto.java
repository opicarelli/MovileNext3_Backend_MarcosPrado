package com.opicarelli.movilenext3.war.marketplace.dto;

import java.io.Serializable;
import java.util.List;

import com.opicarelli.movilenext3.ejb.extension.entity.CategoryTemperature;
import com.opicarelli.movilenext3.ejb.geo.GeoUtils;
import com.opicarelli.movilenext3.ejb.geo.SRIDProjectionEnum;

public class RegionExtensionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Double localityCoordinateX;
	private Double localityCoordinateY;
	private List<CategoryTemperature> supportsCategoryTemperature;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLocalityCoordinateX() {
		return localityCoordinateX;
	}

	public void setLocalityCoordinateX(Double localityCoordinateX) {
		this.localityCoordinateX = localityCoordinateX;
	}

	public Double getLocalityCoordinateY() {
		return localityCoordinateY;
	}

	public void setLocalityCoordinateY(Double localityCoordinateY) {
		this.localityCoordinateY = localityCoordinateY;
	}

	public List<CategoryTemperature> getSupportsCategoryTemperature() {
		return supportsCategoryTemperature;
	}

	public void setSupportsCategoryTemperature(List<CategoryTemperature> supportsCategoryTemperature) {
		this.supportsCategoryTemperature = supportsCategoryTemperature;
	}

	public String getToWkt() {
		return GeoUtils.toWkt(localityCoordinateX, localityCoordinateY, SRIDProjectionEnum.LAT_LON);
	}

}
