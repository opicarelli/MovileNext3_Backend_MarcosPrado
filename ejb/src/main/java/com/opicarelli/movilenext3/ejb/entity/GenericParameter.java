package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_GENERIC_PARAMETER")
public class GenericParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String keyParameter;

	@Column(nullable = false)
	private String valueParameter;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyParameter() {
		return keyParameter;
	}

	public void setKeyParameter(String keyParameter) {
		this.keyParameter = keyParameter;
	}

	public String getValueParameter() {
		return valueParameter;
	}

	public void setValueParameter(String valueParameter) {
		this.valueParameter = valueParameter;
	}

}
