package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_ORDER")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "order_origin_fk"))
	private Locality origin;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "order_destination_fk"))
	private Locality destination;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();

	public Order(Locality origin, Locality destination, List<OrderItem> items) {
		validateInvariants(origin, destination, items);

		setOrigin(origin);
		setDestination(destination);
		setItems(items);
	}

	private void validateInvariants(Locality origin, Locality destination, List<OrderItem> items) {
		Validate.notNull(origin, "Origin must be declared");
		Validate.notNull(destination, "Destination must be declared");
		Validate.notEmpty(items, "Items must be declared");
		Validate.isTrue(items.size() >= 1, "Items must have at least one item");
		Validate.isTrue(!origin.equals(destination), "Origin and Destination must not be equal");
	}

	private void setOrigin(Locality origin) {
		this.origin = origin;
	}

	private void setDestination(Locality destination) {
		this.destination = destination;
	}

	private void setItems(List<OrderItem> items) {
		this.items = items;

		for (OrderItem orderItem : items) {
			orderItem.setOrder(this);
		}
	}

	public Long getId() {
		return id;
	}
}
