package com.opicarelli.movilenext3.war.marketplace.dto;

import java.io.Serializable;

import com.opicarelli.movilenext3.ejb.geo.GeoUtils;
import com.opicarelli.movilenext3.ejb.geo.SRIDProjectionEnum;

public class EstablishmentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String documentNumber;
	private Double localityCoordinateX;
	private Double localityCoordinateY;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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

	public String getToWkt() {
		return GeoUtils.toWkt(localityCoordinateX, localityCoordinateY, SRIDProjectionEnum.LAT_LON);
	}

}
