DashboardCtrl = new function () {

	that = this;

	this.wrapperMap = null;

	this.init = function () {
		this.createMap();
	};

	this.createMap = function () {
		this.wrapperMap = new wrapper.ol.Map();
		this.wrapperMap.create();

		// Interacao Novo Ponto
		var interactionNewClient = new wrapper.ol.Interaction(this.wrapperMap.olMap, "newClient", wrapper.ol.GeometryType.POINT, "Novo Cliente",
			{
				color: "#C2185B",
				onDrawend: this.onInteractionDrawendNewClient
			});

		var interactions = [interactionNewClient];
		this.wrapperMap.createInteraction("map-interaction", interactions);

		this.loadMap();
	};

	this.onInteractionDrawendNewClient = function (evt) {
		var interaction = this;
		var feature = evt.feature;
		var id = Date.now();
		feature.setId(id);

		var wkt = that.wrapperMap.writeFeatureToWKT(feature, { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON });

		$.get("/portal/rest/marketplace/establishments", { pointWkt: wkt })
			.done(function(data) {
				var establishmentsSource = new ol.source.Vector({});
		        var establishmentsLayer = new ol.layer.Vector({ source: establishmentsSource });
		        this.wrapperMap.olMap.addLayer(establishmentsLayer);

		        var wktFormat = new ol.format.WKT();
		        var optProjection = { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON };

		        $.each(data, function (idx, obj) {
		        	var locality = obj.locality;
        			var wkt = locality.toWkt;
        			var feature = wktFormat.readFeature(wkt, optProjection);
        			feature.setId("polyEstablishment_" + obj.id);
        			establishmentsSource.addFeature(feature);
        		});
			});
	};

	this.loadMap = function () {

		var regionsSource = new ol.source.Vector({});
        var regionsLayer = new ol.layer.Vector({ source: regionsSource });
        this.wrapperMap.olMap.addLayer(regionsLayer);

        var wktFormat = new ol.format.WKT();
        var optProjection = { featureProjection: wrapper.ol.ProjectionType.OL3_DEFAULT, dataProjection: wrapper.ol.ProjectionType.LAT_LON };


        $.get("/portal/rest/marketplace/regions", function( data ) {
        	if (!$.isEmptyObject(data)) {
        		$.each(data, function (idx, obj) {
        			var wkt = obj.polygon;
        			var feature = wktFormat.readFeature(wkt, optProjection);
        			feature.setId("polyRegion_" + obj.id);
        			regionsSource.addFeature(feature);
        		});
        	}
        	that.wrapperMap.olMap.updateSize();
        });

	};

}