package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_LEG")
public class Leg implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_route_fk"))
	private Route route;

	@OneToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_origin_fk"))
	private Locality origin;

	@OneToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "leg_destination_fk"))
	private Locality destination;
}
