package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_ROUTE")
public class Route implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "route_worker_fk"))
	private Worker worker;

	@OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Leg> legs = new LinkedList<>();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RouteStatus status = RouteStatus.CREATED;

	public Route(Worker worker, LinkedList<Leg> legs) {
		validateInvariants(worker, legs);

		setWorker(worker);
		setLegs(legs);
	}

	private void validateInvariants(Worker worker, LinkedList<Leg> legs) {
		Validate.notNull(worker, "Worker must be declared");
		Validate.notEmpty(legs, "Legs must be declared");
		Validate.isTrue(legs.size() >= 1, "Legs must have at least one item");
		Validate.isTrue(legs.getFirst().getStepOrder() == 1, "First legs must be the first step order");
	}

	public Long getId() {
		return id;
	}

	private void setWorker(Worker worker) {
		this.worker = worker;
	}

	private void setLegs(LinkedList<Leg> legs) {
		this.legs = legs;
		for (Leg leg : legs) {
			leg.setOrder(this);
		}
	}
}
