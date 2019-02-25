package com.opicarelli.movilenext3.ejb.marketplace.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import com.opicarelli.movilenext3.ejb.extension.entity.CategoryTemperature;

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
	private CategoryTemperature categoryTemperature;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "product_establishment_fk"))
	private Establishment establishment;

	public Product(String name, CategoryTemperature categoryTemperature, Establishment establishment) {
		validateInvariants(name, categoryTemperature, establishment);

		setName(name);
		setCategoryTemperature(categoryTemperature);
		setEstablishment(establishment);
	}

	private void validateInvariants(String name, CategoryTemperature categoryTemperature,
			Establishment establishment) {
		Validate.notEmpty(name, "Name cannot be empty");
		Validate.notNull(categoryTemperature, "CategoryTemperature must have be declared");
		Validate.notNull(establishment, "Establishment must have be declared");
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setCategoryTemperature(CategoryTemperature categoryTemperature) {
		this.categoryTemperature = categoryTemperature;
	}

	private void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}

	public CategoryTemperature getCategoryTemperature() {
		return categoryTemperature;
	}
}
