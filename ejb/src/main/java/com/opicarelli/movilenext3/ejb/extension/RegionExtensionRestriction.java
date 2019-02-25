package com.opicarelli.movilenext3.ejb.extension;

import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Product;

public interface RegionExtensionRestriction {

	public boolean isFeasible(RegionExtension regionExtension, Product product);

	public static enum StandardRegionExtensionRestriction implements RegionExtensionRestriction {

		PRODUCT_TEMPERATURE {
			@Override
			public boolean isFeasible(RegionExtension regionExtension, Product product) {
				return regionExtension.getSupportsCategoryTemperature()
						.contains(product.getCategoryTemperature());
			}
		}

	}
}
