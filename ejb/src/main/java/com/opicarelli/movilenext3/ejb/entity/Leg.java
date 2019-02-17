package com.opicarelli.movilenext3.ejb.entity;

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

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_LEG")
public class Leg implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer stepOrder = Integer.valueOf(1);

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_route_fk"))
	private Route route;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_origin_fk"))
	private Locality origin;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_destination_fk"))
	private Locality destination;

	public Leg(Locality origin, Locality destination) {
		validateInvariants(origin, destination);

		setOrigin(origin);
		setDestination(destination);
	}

	private void validateInvariants(Locality origin, Locality destination) {
		Validate.notNull(origin, "Origin must be declared");
		Validate.notNull(destination, "Destination must be declared");
		Validate.isTrue(!origin.equals(destination), "Origin and Destination must not be equal");
	}

	private void setOrigin(Locality origin) {
		this.origin = origin;
	}

	private void setDestination(Locality destination) {
		this.destination = destination;
	}

	void setOrder(Route route) {
		this.route = route;
	}

	public Integer getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(Integer stepOrder) {
		this.stepOrder = stepOrder;
	}

}
