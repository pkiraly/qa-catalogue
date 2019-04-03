package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

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
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd342.html";

    ind1 = new Indicator("Geospatial reference dimension")
      .setCodes(
        "0", "Horizontal coordinate system",
        "1", "Vertical coordinate system"
      )
      .setMqTag("dimension")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator("Geospatial reference method")
      .setCodes(
        "0", "Geographic",
        "1", "Map projection",
        "2", "Grid coordinate system",
        "3", "Local planar",
        "4", "Local",
        "5", "Geodetic model",
        "6", "Altitude",
        "7", "Method specified in $2",
        "8", "Depth"
      )
      .setMqTag("method");

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

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("name")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("b").setMqTag("units")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("c").setMqTag("latitudeResolution")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("d").setMqTag("longitudeResolution")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("e").setMqTag("obliqueLineLatitude")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("f").setMqTag("obliqueLineLongitude")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("g").setMqTag("longitudeOfCentralMeridian")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("h").setMqTag("latitudeOfProjectionCenter")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("i").setMqTag("falseEasting")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("j").setMqTag("falseNorthing")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("k").setMqTag("scaleFactor")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("l").setMqTag("heightOfPerspective")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("m").setMqTag("azimuthalAngle")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("n").setMqTag("azimuthMeasurePoint")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("o").setMqTag("landsatNumber")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("p").setMqTag("zoneIdentifier")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("q").setMqTag("ellipsoidName")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("r").setMqTag("semiMajorAxis")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("s").setMqTag("denominatorOfFlatteningRatio")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("t").setMqTag("verticalResolution")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("u").setMqTag("verticalEncodingMethod")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("v").setMqTag("localProjection")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("w").setMqTag("georeference")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("2").setBibframeTag("source")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
