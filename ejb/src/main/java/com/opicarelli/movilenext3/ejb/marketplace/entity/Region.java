package com.opicarelli.movilenext3.ejb.marketplace.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_REGION")
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false, columnDefinition = "CLOB")
	private String polygon;

	public Region(String polygon) {
		validateInvariants(polygon);

		setPolygon(polygon);
	}

	private void validateInvariants(String polygon) {
		Validate.notEmpty(polygon, "Polygon must be declared");
	}

	private void setPolygon(String polygon) {
		this.polygon = polygon;
	}

	public Long getId() {
		return id;
	}

	public String getPolygon() {
		return polygon;
	}

}
