package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Geospatial Reference Data
 * http://www.loc.gov/marc/bibliographic/bd342.html
 */
public class Tag342 extends DataFieldDefinition {
	private static Tag342 uniqueInstance;

	private Tag342() {
		initialize();
		postCreation();
	}

	public static Tag342 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag342();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "342";
		label = "Geospatial Reference Data";
		mqTag = "GeospatialReferenceData";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Geospatial reference dimension").setCodes(
			"0", "Horizontal coordinate system",
			"1", "Vertical coordinate system"
		).setMqTag("dimension");
		ind2 = new Indicator("Geospatial reference method").setCodes(
			"0", "Geographic",
			"1", "Map projection",
			"2", "Grid coordinate system",
			"3", "Local planar",
			"4", "Local",
			"5", "Geodetic model",
			"6", "Altitude",
			"7", "Method specified in $2",
			"8", "Depth"
		).setMqTag("method");

		setSubfieldsWithCardinality(
			"a", "Name", "NR",
			"b", "Coordinate units or distance units", "NR",
			"c", "Latitude resolution", "NR",
			"d", "Longitude resolution", "NR",
			"e", "Standard parallel or oblique line latitude", "R",
			"f", "Oblique line longitude", "R",
			"g", "Longitude of central meridian or projection center", "NR",
			"h", "Latitude of projection center or projection origin", "NR",
			"i", "False easting", "NR",
			"j", "False northing", "NR",
			"k", "Scale factor", "NR",
			"l", "Height of perspective point above surface", "NR",
			"m", "Azimuthal angle", "NR",
			"n", "Azimuth measure point longitude or straight vertical longitude from pole", "NR",
			"o", "Landsat number and path number", "NR",
			"p", "Zone identifier", "NR",
			"q", "Ellipsoid name", "NR",
			"r", "Semi-major axis", "NR",
			"s", "Denominator of flattening ratio", "NR",
			"t", "Vertical resolution", "NR",
			"u", "Vertical encoding method", "NR",
			"v", "Local planar, local, or other projection or grid description", "NR",
			"w", "Local planar or local georeference information", "NR",
			"2", "Reference method used", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("name");
		getSubfield("b").setMqTag("units");
		getSubfield("c").setMqTag("latitudeResolution");
		getSubfield("d").setMqTag("longitudeResolution");
		getSubfield("e").setMqTag("obliqueLineLatitude");
		getSubfield("f").setMqTag("obliqueLineLongitude");
		getSubfield("g").setMqTag("longitudeOfCentralMeridian");
		getSubfield("h").setMqTag("latitudeOfProjectionCenter");
		getSubfield("i").setMqTag("falseEasting");
		getSubfield("j").setMqTag("falseNorthing");
		getSubfield("k").setMqTag("scaleFactor");
		getSubfield("l").setMqTag("heightOfPerspective");
		getSubfield("m").setMqTag("azimuthalAngle");
		getSubfield("n").setMqTag("azimuthMeasurePoint");
		getSubfield("o").setMqTag("landsatNumber");
		getSubfield("p").setMqTag("zoneIdentifier");
		getSubfield("q").setMqTag("ellipsoidName");
		getSubfield("r").setMqTag("semiMajorAxis");
		getSubfield("s").setMqTag("denominatorOfFlatteningRatio");
		getSubfield("t").setMqTag("verticalResolution");
		getSubfield("u").setMqTag("verticalEncodingMethod");
		getSubfield("v").setMqTag("localProjection");
		getSubfield("w").setMqTag("georeference");
		getSubfield("2").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
