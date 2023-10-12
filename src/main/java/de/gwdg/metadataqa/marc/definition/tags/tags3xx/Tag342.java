package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

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
    setCompilanceLevels("A");

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

    getSubfield("a")
      .setMqTag("name")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("units")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("latitudeResolution")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("longitudeResolution")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("obliqueLineLatitude")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("obliqueLineLongitude")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("longitudeOfCentralMeridian")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("latitudeOfProjectionCenter")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("falseEasting")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("falseNorthing")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("scaleFactor")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("heightOfPerspective")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("azimuthalAngle")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("azimuthMeasurePoint")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("landsatNumber")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("zoneIdentifier")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("q")
      .setMqTag("ellipsoidName")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("r")
      .setMqTag("semiMajorAxis")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("s")
      .setMqTag("denominatorOfFlatteningRatio")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("t")
      .setMqTag("verticalResolution")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("verticalEncodingMethod")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("v")
      .setMqTag("localProjection")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("w")
      .setMqTag("georeference")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("2")
      .setBibframeTag("source")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
