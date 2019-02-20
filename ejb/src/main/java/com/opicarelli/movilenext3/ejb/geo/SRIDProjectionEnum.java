package com.opicarelli.movilenext3.ejb.geo;

public enum SRIDProjectionEnum {

	/** Projection default openlayer 3 */
	OL3_DEFAULT("EPSG:3857", 3857),

	/** Projection latitude/longitude */
	LAT_LON("EPSG:4326", 4326);

	private String epsg;
	private Integer code;

	private SRIDProjectionEnum(String epsg, Integer code) {
		this.epsg = epsg;
		this.code = code;
	}

	public String getEpsg() {
		return epsg;
	}

	public Integer getCode() {
		return code;
	}
}