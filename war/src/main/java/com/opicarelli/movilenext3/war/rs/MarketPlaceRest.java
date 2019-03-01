package com.opicarelli.movilenext3.war.rs;

import java.lang.reflect.Type;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.opicarelli.movilenext3.ejb.extension.entity.RegionExtension;
import com.opicarelli.movilenext3.ejb.geo.GeoUtils;
import com.opicarelli.movilenext3.ejb.geo.SRIDProjectionEnum;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Establishment;
import com.opicarelli.movilenext3.ejb.marketplace.entity.Region;
import com.opicarelli.movilenext3.ejb.marketplace.service.MarketPlaceService;
import com.opicarelli.movilenext3.war.marketplace.dto.EstablishmentDto;
import com.opicarelli.movilenext3.war.marketplace.dto.RegionExtensionDto;

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
	public List<EstablishmentDto> getEstablishments(@QueryParam("pointWkt") String pointWkt) {
		Point point = GeoUtils.fromWkt(pointWkt, SRIDProjectionEnum.LAT_LON, Point.class);
		Coordinate coordinate = point.getCoordinate();
		List<Establishment> establishments = marketPlaceService.findAllEstablishment(coordinate.getX(),
				coordinate.getY());

		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<EstablishmentDto>>() {
		}.getType();
		List<EstablishmentDto> dtos = modelMapper.map(establishments, listType);
		return dtos;
	}

	@POST
	@Path("/flagregionextension")
	@Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<RegionExtensionDto> flagRegionExtension(@FormParam("enable") Boolean enable) {
		List<RegionExtension> extensions = marketPlaceService.flagRegionExtension(enable);
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<RegionExtensionDto>>() {
		}.getType();
		List<RegionExtensionDto> dtos = modelMapper.map(extensions, listType);
		return dtos;
	}

}
