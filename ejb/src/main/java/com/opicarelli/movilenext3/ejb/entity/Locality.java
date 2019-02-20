package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_LOCALITY")
public class Locality implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double coordinateX;

	@Column(nullable = false)
	private Double coordinateY;

	public Locality(Double coordinateX, Double coordinateY) {
		validateInvariants(coordinateX, coordinateY);

		setCoordinateX(coordinateX);
		setCoordinateY(coordinateY);
	}

	private void validateInvariants(Double coordinateX, Double coordinateY) {
		Validate.notNull(coordinateX, "CoordinateX have be declared");
		Validate.notNull(coordinateY, "CoordinateY have be declared");
	}

	private void setCoordinateX(Double coordinateX) {
		this.coordinateX = coordinateX;
	}

	private void setCoordinateY(Double coordinateY) {
		this.coordinateY = coordinateY;
	}

	public Double getCoordinateX() {
		return coordinateX;
	}

	public Double getCoordinateY() {
		return coordinateY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinateX == null) ? 0 : coordinateX.hashCode());
		result = prime * result + ((coordinateY == null) ? 0 : coordinateY.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locality other = (Locality) obj;
		if (coordinateX == null) {
			if (other.coordinateX != null)
				return false;
		} else if (!coordinateX.equals(other.coordinateX))
			return false;
		if (coordinateY == null) {
			if (other.coordinateY != null)
				return false;
		} else if (!coordinateY.equals(other.coordinateY))
			return false;
		return true;
	}

}
