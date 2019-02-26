DashboardCtrl = new function () {

	that = this;

	this.wrapperMap = null;

	this.init = function () {
		this.createMap();
	};

	this.createMap = function () {
		this.wrapperMap = new wrapper.ol.Map();
		this.wrapperMap.create();

		// Interacao Poligono
		var interactionPoly = new wrapper.ol.Interaction(this.wrapperMap.olMap, "poly", wrapper.ol.GeometryType.POLYGON, "Novo poligono",
			{
				color: "#303F9F",
				onDrawend: this.onInteractionDrawendPoly
			});

		// Interacao Nova Linha
		var interactionNewLineSegment = new wrapper.ol.Interaction(this.wrapperMap.olMap, "newLineSegment", wrapper.ol.GeometryType.LINE_STRING, "Nova linha",
			{
				color: "#D32F2F",
				onDrawend: this.onInteractionDrawendNewLineSegment,
				onPointermove: this.onInteractionPointermoveNewLineSegment
			});

		// Interacao Novo Ponto
		var interactionNewPoint = new wrapper.ol.Interaction(this.wrapperMap.olMap, "newPoint", wrapper.ol.GeometryType.POINT, "Novo ponto",
			{
				color: "#C2185B",
				onDrawend: this.onInteractionDrawendNewPoint,
				onPointermove: this.onInteractionPointermoveNewLineSegment
			});

		var interactions = [interactionPoly, interactionNewLineSegment, interactionNewPoint];
		this.wrapperMap.createInteraction("map-interaction", interactions);

		this.loadMap();
	};

	this.onInteractionDrawendPoly = function (evt) {
		var interaction = this;
		var feature = evt.feature;
		var id = Date.now();
		feature.setId(id);

		console.log(feature);

		var features = that.wrapperMap.getAllDrawerFeatures("poly");
		$.each(features, function (idx, obj) {
			var wkt = that.wrapperMap.writeFeatureToWKT(obj, { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON });
			var polygon = { wktValue: wkt };
			console.log(polygon);
		});
	};

	this.onInteractionDrawendNewLineSegment = function () {

	};

	this.onInteractionDrawendNewPoint = function (evt) {
		var interaction = this;
		var feature = evt.feature;
		var id = Date.now();
		feature.setId(id);

		console.log(feature);

		var features = that.wrapperMap.getAllDrawerFeatures("newPoint");
		$.each(features, function (idx, obj) {
			var wkt = that.wrapperMap.writeFeatureToWKT(obj, { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON });
			var polygon = { wktValue: wkt };
			console.log(polygon);
		});
	};

	this.onInteractionPointermoveNewLineSegment = function (evt) {
		var interaction = this;
		if (evt.dragging || interaction.drawing) {
			return;
		}
		var olMap = evt.map;
		var feature = olMap.forEachFeatureAtPixel(evt.pixel,
			function (feature, layer) {
				return feature;
			},
			null,
			function (layer) {
				return layer == that.wrapperMap.primariesLayer;
			}
		);
		var hit = (feature) ? true : false;
		interaction.olDraw.setActive(hit);
	};

	this.loadMap = function () {

		this.wrapperMap.olMap.updateSize();
	};

}