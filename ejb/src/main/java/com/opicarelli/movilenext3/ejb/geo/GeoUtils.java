package com.opicarelli.movilenext3.ejb.geo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeoUtils {

	// Suppresses default constructor, ensuring non-instantiability.
	private GeoUtils() {
	}

	public static boolean contains(String polygonWkt, double coordinateX, double coordinateY) {
		return contains(polygonWkt, coordinateX, coordinateY, SRIDProjectionEnum.LAT_LON);
	}

	public static boolean contains(String polygonWkt, double coordinateX, double coordinateY, SRIDProjectionEnum srid) {
		boolean result = false;
		try {
			PrecisionModel precisionModel = new PrecisionModel();
			GeometryFactory geomFactory = new GeometryFactory(precisionModel, srid.getCode());
			WKTReader wktReader = new WKTReader(geomFactory);

			Polygon polygon = (Polygon) wktReader.read(polygonWkt);
			Point pointFromCoordinate = geomFactory.createPoint(new Coordinate(coordinateX, coordinateY));
			if (polygon.contains(pointFromCoordinate)) {
				result = true;
			}
		} catch (ParseException e) {
			// do nothing
		}
		return result;
	}

}
