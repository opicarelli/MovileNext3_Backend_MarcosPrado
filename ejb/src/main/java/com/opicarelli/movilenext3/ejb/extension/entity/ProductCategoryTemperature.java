package com.opicarelli.movilenext3.ejb.extension.entity;

public enum ProductCategoryTemperature {

	FROZEN(-18, -1),

	COOLED(0, 18),

	CLIMATE(19, 36),

	WARM(37, 54);

	private int min;
	private int max;

	private ProductCategoryTemperature(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}
