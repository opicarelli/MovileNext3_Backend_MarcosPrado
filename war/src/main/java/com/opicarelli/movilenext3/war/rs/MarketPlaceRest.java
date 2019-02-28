package com.opicarelli.movilenext3.war.rs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;
import com.opicarelli.movilenext3.ejb.marketplace.service.MarketPlaceService;

@Stateless
@Path("/marketplace")
public class MarketPlaceRest {

	@EJB
	private MarketPlaceService marketPlaceService;

	@GET
	@Path("/regions")
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Region> getRegions() {
		List<Region> regions = marketPlaceService.findAllRegion();
		return regions;
	}

}
