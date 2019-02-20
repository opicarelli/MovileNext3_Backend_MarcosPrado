package com.opicarelli.movilenext3.ejb.marketplace.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import com.opicarelli.movilenext3.ejb.entity.Locality;
import com.opicarelli.movilenext3.ejb.geo.GeoUtils;

@Entity
@Table(name = "T_ESTABLISHMENT", uniqueConstraints = {
		@UniqueConstraint(columnNames = "documentNumber", name = "establishment_documentnumber_uk") })
public class Establishment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String documentNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "establishment_locality_fk"))
	private Locality locality;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "establishment_region_fk"))
	private Region region;

	public Establishment(String documentNumber, Locality locality, Region region) {
		validateInvariants(documentNumber, locality, region);

		setDocumentNumber(documentNumber);
		setLocality(locality);
		setRegion(region);
	}

	private void validateInvariants(String documentNumber, Locality locality, Region region) {
		Validate.notNull(documentNumber, "Document number must be declared");
		Validate.notNull(locality, "Locality must be declared");
		Validate.notNull(region, "Region must be declared");
		Validate.isTrue(GeoUtils.contains(region.getPolygon(), locality.getCoordinateX(), locality.getCoordinateY()),
				"Locality is out side of region");
	}

	private void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	private void setLocality(Locality locality) {
		this.locality = locality;
	}

	private void setRegion(Region region) {
		this.region = region;
	}

	public Long getId() {
		return id;
	}
}
