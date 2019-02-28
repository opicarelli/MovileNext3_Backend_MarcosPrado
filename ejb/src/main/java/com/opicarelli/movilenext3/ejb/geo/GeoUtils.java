package com.opicarelli.movilenext3.ejb.geo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

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

	public static String toWkt(double coordinateX, double coordinateY, SRIDProjectionEnum srid) {
		PrecisionModel precisionModel = new PrecisionModel();
		GeometryFactory geomFactory = new GeometryFactory(precisionModel, srid.getCode());
		WKTWriter wktWriter = new WKTWriter();
		Point pointFromCoordinate = geomFactory.createPoint(new Coordinate(coordinateX, coordinateY));
		return wktWriter.write(pointFromCoordinate);
	}

	public static <T extends Geometry> T fromWkt(String wkt, SRIDProjectionEnum srid, Class<T> type) {
		T geometry = null;
		try {
			PrecisionModel precisionModel = new PrecisionModel();
			GeometryFactory geomFactory = new GeometryFactory(precisionModel, srid.getCode());
			WKTReader wktReader = new WKTReader(geomFactory);
			geometry = type.cast(wktReader.read(wkt));
		} catch (ParseException e) {
			// do nothing
		}
		return geometry;
	}
}
