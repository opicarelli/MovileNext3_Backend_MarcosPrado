package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import com.opicarelli.movilenext3.ejb.extension.entity.ProductCategoryTemperature;

@Entity
@Table(name = "T_PRODUCT")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProductCategoryTemperature categoryTemperature;

	public Product(String name, ProductCategoryTemperature categoryTemperature) {
		validateInvariants(name, categoryTemperature);

		setName(name);
		setCategoryTemperature(categoryTemperature);
	}

	private void validateInvariants(String name, ProductCategoryTemperature categoryTemperature) {
		Validate.notEmpty(name, "Name cannot be empty");
		Validate.notNull(categoryTemperature, "CategoryTemperature must have be declared");
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setCategoryTemperature(ProductCategoryTemperature categoryTemperature) {
		this.categoryTemperature = categoryTemperature;
	}

}
