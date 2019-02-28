package com.opicarelli.movilenext3.war.rs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import com.opicarelli.movilenext3.ejb.geo.GeoUtils;
import com.opicarelli.movilenext3.ejb.geo.SRIDProjectionEnum;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
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

	@GET
	@Path("/establishments")
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Establishment> getEstablishments(@QueryParam("pointWkt") String pointWkt) {
		Point point = GeoUtils.fromWkt(pointWkt, SRIDProjectionEnum.LAT_LON, Point.class);
		Coordinate coordinate = point.getCoordinate();
		List<Establishment> establishments = marketPlaceService.findAllEstablishment(coordinate.getX(),
				coordinate.getY());
		return establishments;
	}

}
