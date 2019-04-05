package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.CartographicDataSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Coded Cartographic Mathematical Data
 * http://www.loc.gov/marc/bibliographic/bd034.html
 */
public class Tag034 extends DataFieldDefinition {

  private static Tag034 uniqueInstance;

  private Tag034() {
    initialize();
    postCreation();
  }

  public static Tag034 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag034();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "034";
    label = "Coded Cartographic Mathematical Data";
    bibframeTag = "Scale";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd034.html";

    ind1 = new Indicator("Type of scale")
      .setCodes(
        "0", "Scale indeterminable/No scale recorded",
        "1", "Single scale",
        "3", "Range of scales"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new Code(" ", "Not specified")
      ))
      .setHistoricalCodes(
        "2", "Two or more scales (BK, MP, SE) [OBSOLETE]"
      )
      .setMqTag("typeOfScale")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    ind2 = new Indicator("Type of ring")
      .setCodes(
        " ", "Not applicable",
        "0", "Outer ring",
        "1", "Exclusion ring"
      )
      .setMqTag("typeOfRing")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Category of scale", "NR",
      "b", "Constant ratio linear horizontal scale", "R",
      "c", "Constant ratio linear vertical scale", "R",
      "d", "Coordinates - westernmost longitude", "NR",
      "e", "Coordinates - easternmost longitude", "NR",
      "f", "Coordinates - northernmost latitude", "NR",
      "g", "Coordinates - southernmost latitude", "NR",
      "h", "Angular scale", "R",
      "j", "Declination - northern limit", "NR",
      "k", "Declination - southern limit", "NR",
      "m", "Right ascension - eastern limit", "NR",
      "n", "Right ascension - western limit", "NR",
      "p", "Equinox", "NR",
      "r", "Distance from earth", "NR",
      "s", "G-ring latitude", "R",
      "t", "G-ring longitude", "R",
      "x", "Beginning date", "NR",
      "y", "Ending date", "NR",
      "z", "Name of extraterrestrial body", "NR",
      "0", "Authority record control number or standard number", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setCodes(
      "a", "Linear scale",
      "b", "Angular scale",
      "z", "Other type of scale"
    );
    getSubfield("2").setCodeList(CartographicDataSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("category")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("b").setMqTag("linearHorizontalScale")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("c").setMqTag("linearVerticalScale")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("d").setMqTag("westernmostLongitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("e").setMqTag("easternmostLongitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("f").setMqTag("northernmostLatitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("g").setMqTag("southernmostLatitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("h").setMqTag("angularScale")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("j").setMqTag("declinationNorthernLimit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("k").setMqTag("declinationSouthernLimit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("m").setMqTag("rightAscensionEasternLimit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("n").setMqTag("rightAscensionWesternLimit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("p").setMqTag("equinox")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("r").setMqTag("distanceFromEarth");
    getSubfield("s").setMqTag("gRingLatitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("t").setMqTag("gRingLongitude")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("x").setMqTag("beginningDate");
    getSubfield("y").setMqTag("endingDate");
    getSubfield("z").setMqTag("extraterrestrialBody");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source");
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
