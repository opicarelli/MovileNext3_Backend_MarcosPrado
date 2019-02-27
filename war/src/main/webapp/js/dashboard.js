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
				onDrawend: this.onInteractionDrawendNewLineSegment
			});

		// Interacao Novo Ponto
		var interactionNewPoint = new wrapper.ol.Interaction(this.wrapperMap.olMap, "newPoint", wrapper.ol.GeometryType.POINT, "Novo ponto",
			{
				color: "#C2185B",
				onDrawend: this.onInteractionDrawendNewPoint
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

		// Just to debug
		var wkt = that.wrapperMap.writeFeatureToWKT(feature, { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON });
		var polygon = { wktValue: wkt };
		console.log(polygon);
	};

	this.onInteractionDrawendNewLineSegment = function () {

	};

	this.onInteractionDrawendNewPoint = function (evt) {
		var interaction = this;
		var feature = evt.feature;
		var id = Date.now();
		feature.setId(id);

		var wkt = that.wrapperMap.writeFeatureToWKT(feature, { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON });
		var polygon = { wktValue: wkt };
		console.log(polygon);
	};

	this.loadMap = function () {

		var regionsSource = new ol.source.Vector({});
        var regionsLayer = new ol.layer.Vector({ source: regionsSource });
        this.wrapperMap.olMap.addLayer(regionsLayer);

        var wktFormat = new ol.format.WKT();
        var optProjection = { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON };

        var regions = [];
        regions.push(
        {
        	id: 1,
        	polygon: 'POLYGON((-47.1010145892716 -22.81855527197949,-47.092474435768175 -22.828800068388006,-47.08955619235998 -22.83235985574656,-47.09509227176672 -22.838055321834787,-47.10474822422032 -22.83623596274866,-47.10607859989171 -22.83493075537399,-47.10586402317052 -22.822075771583116,-47.1010145892716 -22.81855527197949))'
        },
        {
        	id: 2,
        	polygon: 'POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))'
        }
        );
        $.each(regions, function (idx, obj) {
            var wkt = obj.polygon;
            var feature = wktFormat.readFeature(wkt, optProjection);
            feature.setId("polyRegion_" + obj.id);
            regionsSource.addFeature(feature);
        });

		this.wrapperMap.olMap.updateSize();
	};

}