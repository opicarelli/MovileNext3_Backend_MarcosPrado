package com.opicarelli.movilenext3.ejb.extension.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.opicarelli.movilenext3.ejb.entity.Locality;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;

@Entity
@Table(name = "T_REGION_EXTENSION")
public class RegionExtension implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "regionextension_locality_fk"))
	private Locality locality;

	@OneToMany
	@JoinTable(name = "regionextension_regions", joinColumns = @JoinColumn(name = "regionextension_id"), inverseJoinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"), foreignKey = @ForeignKey(name = "regionextension_regions_regionextension_fk"), inverseForeignKey = @ForeignKey(name = "regionextension_regions_region_fk"))
	private List<Region> regions;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "regionextension_supports", joinColumns = @JoinColumn(name = "regionextension_id", referencedColumnName = "id"), foreignKey = @ForeignKey(name = "regionextension_supports_regionextension_fk"))
	@Column(name = "support")
	@Enumerated(EnumType.STRING)
	private List<ProductCategoryTemperature> supportsProductCategoryTemperature;

	public RegionExtension(Locality locality, List<Region> regions, List<ProductCategoryTemperature> supports) {
		this.locality = locality;
		this.regions = regions;
		this.supportsProductCategoryTemperature = supports;
	}

	public Long getId() {
		return id;
	}

}
