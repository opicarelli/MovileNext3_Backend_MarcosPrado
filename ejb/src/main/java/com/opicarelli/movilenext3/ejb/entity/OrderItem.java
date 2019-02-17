package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_ORDER_ITEM")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "orderitem_product_fk"))
	private Product product;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "orderitem_order_fk"))
	private Order order;

	public OrderItem(Product product) {
		validateInvariants(product);

		setProduct(product);
	}

	private void validateInvariants(Product product) {
		Validate.notNull(product, "Product must have be declared");
	}

	void setOrder(Order order) {
		this.order = order;
	}

	private void setProduct(Product product) {
		this.product = product;
	}
}
