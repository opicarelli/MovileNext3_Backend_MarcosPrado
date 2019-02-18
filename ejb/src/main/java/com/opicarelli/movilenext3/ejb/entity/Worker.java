package com.opicarelli.movilenext3.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "T_WORKER", uniqueConstraints = {
		@UniqueConstraint(columnNames = "documentNumber", name = "worker_documentnumber_uk") })
public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String documentNumber;

	public Worker(String documentNumber) {
		validateInvariants(documentNumber);

		setDocumentNumber(documentNumber);
	}

	private void validateInvariants(String documentNumber) {
		Validate.notNull(documentNumber, "Document number must be declared");
	}

	private void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
}
