// packages
wrapper = {};
wrapper.ol = {};
wrapper.ol.wkt = {};

// Map
wrapper.ol.Map = function (target, targetOverlay) {

	var that = this;

	this.target = (target) ? target : "map";

	this.targetOverlay = (targetOverlay) ? targetOverlay : "map-overlay";

	this.osm = new ol.source.OSM();

	this.rasterLayer = new ol.layer.Tile({ source: this.osm });

	this.primariesSource = new ol.source.Vector({});

	this.primariesLayer = new ol.layer.Vector({ source: this.primariesSource });

	this.equipmentsSource = new ol.source.Vector({});

	this.equipmentsLayer = new ol.layer.Vector({ source: this.equipmentsSource });

	this.layers = [this.rasterLayer, this.primariesLayer, this.equipmentsLayer];

	this.drawer = null;

	this.olMap = null;

	this.overlay = null;

	this.create = function () {
		$("#" + this.target).html("");
		$("#" + this.target).append("<div id='" + this.targetOverlay + "' class='map-overlay'>");
		this.olMap = new ol.Map({
			layers: this.layers,
			target: this.target,
			controls: ol.control.defaults().extend([
				new ol.control.FullScreen({source: "map-fullscreen"})
			]),
			view: new ol.View({
				center: ol.proj.transform([-47.09388256072998, -22.826385302709156],
					wrapper.ol.ProjectionType.LAT_LON,
					wrapper.ol.ProjectionType.OL3_DEFAULT),
				zoom: 14
			}),
			eventListeners: {
				featureclick: function (e) {
					console.log("Map says: " + e.feature.id + " clicked on " + e.feature.layer.name);
				}
			}
		});

		// Overlay
		this.overlay = new ol.Overlay({
			element: document.getElementById(this.targetOverlay),
			positioning: "bottom-left"
		});
		this.overlay.setMap(this.olMap);

		// Pointer Move
		this.olMap.on("pointermove", function (evt) {
			var drawer = that.drawer;
			if (drawer) {
				var interaction = drawer.actualInteraction;
				if (interaction && interaction.onPointermove) {
					interaction.onPointermove(evt);
				}
			}

			var feature = that.olMap.forEachFeatureAtPixel(evt.pixel, function (feature) {
				that.overlay.setPosition(evt.coordinate);
				return feature;
			});

			if (feature) {
				var wrapperObj = feature.get("wrapperObj");
				var element = that.overlay.getElement();
				if (element) {
					if (wrapperObj && wrapperObj.overlayable) {
						element.style.display = "";
						element.innerHTML = (wrapperObj.tooltipText ? wrapperObj.tooltipText : wrapperObj.id); //shows id tooltip
					} else {
						element.style.display = "none";
						element.innerHTML = "";
					}
				}
				document.body.style.cursor = "pointer";
			} else {
				var element = that.overlay.getElement();
				if (element) {
					element.style.display = "none";
					element.innerHTML = "";
				}
				document.body.style.cursor = "";
			}
		});

		// Map Click
		this.olMap.on("click", function (evt) {
			var drawer = that.drawer;
			if (drawer) {
				if (drawer.mode == wrapper.ol.Drawer.Mode.ERASE) {
					that.setEraseMode(evt);
				}
			}
		});

		// Map Double Click
		this.olMap.on("dblclick", function (evt) {
			var feature = that.olMap.forEachFeatureAtPixel(evt.pixel, function (feature) {
				return feature;
			});

			if (feature) {
				var wrapperObj = feature.get("wrapperObj");
				if (wrapperObj && wrapperObj.doubleClick) {
					wrapperObj.doubleClick(evt, feature);
					return false;
				}
			}
		});

		this.equipmentsLayer.setZIndex(1);
	};

	this.addPrimaryFeature = function (feature) {

		// Zoom to first line
		this.olMap.getView().animate({
			center: this.getPrimaryCenter(feature),
			duration: 1000,
			zoom: 14
		});

		// Add feature
		this.primariesSource.addFeature(feature);
	};

	this.getPrimaryCenter = function (feature) {
		var extent = feature.getGeometry().getExtent();
		var center = ol.extent.getCenter(extent);
		return center;
	};

	this.addEquipmentFeature = function (feature) {
		this.equipmentsSource.addFeature(feature);
	};

	this.createInteraction = function (target, interactions) {
		this.drawer = new wrapper.ol.Drawer(target, interactions);
		this.drawer.build();
	};

	this.setEraseMode = function (evt) {
		var features = [];
		that.olMap.forEachFeatureAtPixel(evt.pixel, function (feature) {
			if (feature.get("isInteraction")) {
				features.push(feature);
			}
		});

		if ($.isEmptyObject(features)) {
			return;
		}

		$.each(features, function (idx, feature) {
			that.drawer.interactionsMap.forEach(function (v, k) {
				try {
					v.layer.getSource().removeFeature(feature);
					return false;
				}
				catch (err) {
					// feature does not found
				}
			});
		});

	}

	this.removeAllDrawerFeatures = function () {
		if (this.drawer) {
			this.drawer.interactionsMap.forEach(function (val, key, map) {
				val.layer.getSource().clear();
			});
		}
	}

	this.removeDrawerFeatureByKeyAndId = function (key, id) {
		if (this.drawer) {
			if (key && id) {
				var features = this.drawer.interactionsMap.get(key).layer.getSource().getFeatures();
				for (var i = 0; i < features.length; i++) {
					if (features[i].id_ == id) {
						this.drawer.interactionsMap.get(key).layer.getSource().removeFeature(features[i]);
						return true;
					}
				}
			}
		}
		return false;
	};

	this.getAllDrawerFeatures = function (key) {
		if (this.drawer) {
			if (key) {
				return this.drawer.interactionsMap.get(key).layer.getSource().getFeatures();
			} else {
				var result = [];
				this.drawer.interactionsMap.forEach(function (v, k) {
					var features = v.layer.getSource().getFeatures();
					result = result.concat(features);
				});
				return result;
			}
		}
		return [];
	};

	this.writeFeatureToWKT = function (feature, optProjection) {
		var format = new ol.format.WKT();
		var result = format.writeFeature(feature, optProjection);
		return result;
	};

	this.writeFeaturesToWKT = function (features, optProjection) {
		var format = new ol.format.WKT();
		var result = format.writeFeatures(features, optProjection);
		return result;
	};

	this.writeFeaturesToGeoJSON = function (features, optProjection) {
		var format = new ol.format.GeoJSON();
		var result = format.writeFeatures(features, optProjection);
		return result;
	};

};

wrapper.ol.Drawer = function (target, interactions) {

	var that = this;

	this.target = (target) ? target : "map-interaction";
	this.interactions = (interactions) ? interactions : [];
	this.interactionsMap = new Map();
	this.actualInteraction = null;
	this.mode = wrapper.ol.Drawer.Mode.NONE;

	this.build = function () {

		var divMapInteraction = $("#" + this.target);
		divMapInteraction.html("");
		var divGroup = $("<div class='btn-group btn-group-toggle'>");
		divMapInteraction.append(divGroup);

		// Create none interaction
		var btnNone = $("<button type='button' class='btn btn-secondary active' id='noneInteraction'>Nenhum</button>");
		btnNone.prepend("<i class='fa fa-circle' aria-hidden='true'></i>");
		btnNone.bind("click", this.noneInteractions);
		divGroup.append(btnNone);

		var btnErase = $("<button type='button' class='btn btn-secondary' id='eraseInteraction'>Apagar</button>");
		btnErase.prepend("<i class='fa fa-eraser' aria-hidden='true'></i>");
		btnErase.bind("click", this.eraseFeature);
		divGroup.append(btnErase);

		// Create interactions
		if (!$.isEmptyObject(this.interactions)) {
			for (var i = 0; i < this.interactions.length; i++) {
				var item = this.interactions[i];
				this.addInteraction(item);

				var btn = $("<button type='button' class='btn btn-secondary' id='" + item.key + "'></button>");
				btn.text(item.label);
				btn.prepend("<i class='fa fa-square' aria-hidden='true' style='color:" + item.color + "'></i>");
				btn.bind("click", { interaction: item }, this.activate);
				divGroup.append(btn);
			}
		}
	};

	this.eraseFeature = function () {
		that.mode = wrapper.ol.Drawer.Mode.ERASE;

		var wasActive = $(this).hasClass("active");
		if (!wasActive) {

			that.disableInteractions();
			// Active
			$(this).addClass("active");
		}
	};

	this.addInteraction = function (interaction) {
		this.interactionsMap.set(interaction.key, interaction);
	};

	this.noneInteractions = function (e) {
		that.mode = wrapper.ol.Drawer.Mode.NONE;

		var wasActive = $(this).hasClass("active");
		if (!wasActive) {

			that.disableInteractions();
			// Active
			$(this).addClass("active");
		}
	};

	this.disableInteractions = function (e) {
		// Disable all (buttons and interactions)
		var divMapInteraction = $("#" + that.target);
		divMapInteraction.find("button").removeClass("active");
		that.interactionsMap.forEach(function (val, key, map) {
			val.olDraw.setActive(false);
			val.drawing = false;
		});

		// Actual null
		that.actualInteraction = null;
	};

	this.activate = function (e) {
		var thisBtn = this;

		var divMapInteraction = $("#" + that.target);
		divMapInteraction.find("button").removeClass("active");

		var interaction = e.data.interaction;

		that.interactionsMap.forEach(function (val, key) {
			if (interaction.key === key) {
				var active = !val.olDraw.getActive();
				val.olDraw.setActive(active);
				if (active) {
					$(thisBtn).addClass("active");
					that.actualInteraction = val;
					that.mode = wrapper.ol.Drawer.Mode.DRAW;
				} else {
					divMapInteraction.find("button").first().addClass("active");
				}
			} else {
				val.olDraw.setActive(false);
			}
		});
	};

};

// WKT Point
wrapper.ol.wkt.Point = function (id, wkt, options) {

	this.id = id;
	this.wkt = wkt;
	this.style = (options && options.style) ? options.style : wrapper.ol.Style.getPoint();
	this.overlayable = (options && options.overlayable) ? options.overlayable : false;
	this.tooltipText = (options && options.tooltipText) ? options.tooltipText : "";
	this.doubleClick = (options && options.doubleClick) ? options.doubleClick : null;
	if (options.extend) {
		$.extend(this, options.extend);
	}

	this.toFeature = function () {
		var wktFormat = new ol.format.WKT();
		var feature = wktFormat.readFeature(this.wkt);
		feature.setId(id);
		feature.setStyle(this.style);
		feature.set("wrapperObj", this);
		return feature;
	};
};

// WKT Multi Line Segment
wrapper.ol.wkt.MultiLineSegment = function (id, wkt, options) {

	this.id = id;
	this.wkt = wkt;
	this.color = (options && options.color) ? options.color : "#0036FF";
	this.width = (options && options.width) ? options.width : 3;
	this.style = (options && options.style) ? options.style : wrapper.ol.Style.getLineSegment(this.color, this.width);
	this.overlayable = (options && options.overlayable) ? options.overlayable : false;
	this.doubleClick = (options && options.doubleClick) ? options.doubleClick : null;
	if (options && options.extend) {
		$.extend(this, options.extend);
	}

	this.toFeature = function () {
		var wktFormat = new ol.format.WKT();
		var feature = wktFormat.readFeature(this.wkt);
		feature.setId(id);
		feature.setStyle(this.style);
		feature.set("wrapperObj", this);
		return feature;
	};
};

wrapper.ol.Control = function (olMap) {
	this.olMap.controls
}

wrapper.ol.Interaction = function (olMap, key, type, label, options) {

	var that = this;

	this.olMap = olMap;
	this.key = key;
	this.type = type;
	this.label = (label) ? label : key;
	this.color = (options && options.color) ? options.color : "#0036FF";
	this.style = (options && options.style) ? options.style : wrapper.ol.Style.getPolygon(this.color);
	this.drawing = false;
	this.params = new Map();

	// OL definitions
	this.collection = new ol.Collection();
	this.source = new ol.source.Vector({ features: this.collection });
	this.layer = new ol.layer.Vector({ source: this.source, style: this.style });
	this.layer.setMap(this.olMap);

	//TODO Disabled... Does not work with finishCondition
	//this.maxPoints = (options && options.maxPoints) ? options.maxPoints : null;

	// OL Functions
	this.onDrawend = (options && options.onDrawend) ? options.onDrawend : null;
	this.onPointermove = (options && options.onPointermove) ? options.onPointermove : null;;
	this.onFinishCondition = (options && options.onFinishCondition) ? options.onFinishCondition : null;

	// Set OL Options
	var olOptions = { features: this.collection, type: this.type };
	if (this.onFinishCondition) {
		$.extend(olOptions, { finishCondition: this.onFinishCondition });
	}
	//TODO Disabled... Does not work with finishCondition
	//if (this.maxPoints) {
	//	$.extend(olOptions, { maxPoints: this.maxPoints });
	//}

	// Create OL Draw
	this.olDraw = new ol.interaction.Draw(olOptions);
	this.olDraw.setActive(false);
	this.olMap.addInteraction(this.olDraw);

	// Enable modify
	this.modify = new ol.interaction.Modify({
		features: this.collection
	});
	this.olMap.addInteraction(this.modify);

	this.olDraw.on("drawend", function (evt) {
		evt.feature.set("isInteraction", true);
		if (that.onDrawend) {
			that.onDrawend(evt);
		}
		that.drawing = false;
	});

	this.olDraw.on("drawstart", function (evt) {
		that.drawing = true;
	});

};

// ol.geom.GeometryType is not exposed
wrapper.ol.Drawer.Mode = {
	NONE: "NONE",
	DRAW: "DRAW",
	SELECT: "SELECT",
	ERASE: "ERASE"
};

// ol.geom.GeometryType is not exposed
wrapper.ol.GeometryType = {
	POINT: /* {ol.geom.GeometryType.POINT} */ "Point",
	POLYGON: /* {ol.geom.GeometryType.POLYGON} */ "Polygon",
	LINE_STRING: /* {ol.geom.GeometryType.LINE_STRING} */ "LineString"
};

wrapper.ol.ProjectionType = {
	/** Projection default ol3  */
	OL3_DEFAULT: "EPSG:3857",
	/** Projection latitude/longitude  */
	LAT_LON: "EPSG:4326"
};

wrapper.ol.Style = {

	defaultColor: "#9C27B0",
	defaultWidth: 6,

	getPoint: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				fill: new ol.style.Fill({ color: color }),
				stroke: new ol.style.Stroke({ color: "rgba(0,0,0,1)" }),
				points: 1,
				radius: width,
				angle: 0
			})
		});
		return style;
	},

	getSquare: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				fill: new ol.style.Fill({ color: color }),
				stroke: new ol.style.Stroke({ color: "rgba(0,0,0,1)" }),
				points: 4,
				radius: width,
				angle: 0,
				angle: Math.PI / 4
			})
		});
		return style;
	},

	getTriangle: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				fill: new ol.style.Fill({ color: color }),
				stroke: new ol.style.Stroke({ color: "rgba(0,0,0,1)" }),
				points: 3,
				radius: width,
				angle: 0
			})
		});
		return style;
	},

	getStar: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				fill: new ol.style.Fill({ color: color }),
				stroke: new ol.style.Stroke({ color: "rgba(0,0,0,1)" }),
				points: 5,
				radius: width,
				radius2: 4,
				angle: 0
			})
		});
		return style;
	},

	getX: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				stroke: new ol.style.Stroke({ color: color, width: 3 }),
				points: 4,
				radius: width,
				radius2: 0,
				angle: Math.PI / 4
			})
		});
		return style;
	},

	getCircle: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				stroke: new ol.style.Stroke({ color: color, width: 3 }),
				points: 40,
				radius: width,
				radius2: 0,
				angle: 0
			})
		});
		return style;
	},

	getCross: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = new ol.style.Style({
			image: new ol.style.RegularShape({
				fill: new ol.style.Fill({ color: color }),
				stroke: new ol.style.Stroke({ color: "rgba(255,255,255,1)" }),
				points: 4,
				radius: width,
				radius2: 0,
				angle: 0
			})
		});
		return style;
	},

	getLineSegment: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = [
				new ol.style.Style({ // Back
					stroke: new ol.style.Stroke({ color: "rgba(255,255,255,1)", width: width + 2 })
				}),
				new ol.style.Style({ // Front
					stroke: new ol.style.Stroke({ color: color, width: width })
				}),
		];
		return style;
	},

	getLineSegmentSquare: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = [
			new ol.style.Style({ // Back
				stroke: new ol.style.Stroke({ color: "rgba(255,255,255,1)", width: width + 2, lineCap: "square" })
			}),
			new ol.style.Style({ // Front
				stroke: new ol.style.Stroke({ color: color, width: width, lineCap: "square" })
			}),
		];
		return style;
	},

	getPolygon: function (pColor, pWidth) {
		var color = (pColor) ? pColor : wrapper.ol.Style.defaultColor;
		var width = (pWidth) ? pWidth : wrapper.ol.Style.defaultWidth;
		var style = [
			new ol.style.Style({ // Back
				fill: new ol.style.Fill({ color: "rgba(255,255,255,0.2)" }),
				stroke: new ol.style.Stroke({ color: "rgba(255,255,255,1)", width: 4 }),
				image: new ol.style.Circle({ radius: width + 2, fill: new ol.style.Fill({ color: "rgba(255,255,255,1)" }) }) // GeometryType.POINT
			}),
			new ol.style.Style({ // Front
				fill: new ol.style.Fill({ color: "rgba(255,255,255,0.2)" }),
				stroke: new ol.style.Stroke({ color: color, width: 2 }),
				image: new ol.style.Circle({ radius: width, fill: new ol.style.Fill({ color: color }) }) // GeometryType.POINT
			})
		];
		return style;
	},

};