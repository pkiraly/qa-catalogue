package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.MusicalCompositionSourceCodes;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Form of Musical Composition Code
 * http://www.loc.gov/marc/bibliographic/bd047.html
 */
public class Tag047 extends DataFieldDefinition {

  private static Tag047 uniqueInstance;

  private Tag047() {
    initialize();
    postCreation();
  }

  public static Tag047 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag047();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "047";
    label = "Form of Musical Composition Code";
    bibframeTag = "GenreForm";
    mqTag = "MusicalCompositionForm";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd047.html";

    ind1 = new Indicator();

    ind2 = new Indicator("Source of code")
      .setCodes(
        " ", "MARC musical composition code",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("sourceOfCode");

    setSubfieldsWithCardinality(
      "a", "Form of musical composition code", "R",
      "2", "Source of code", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(MusicalCompositionSourceCodes.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);
    getSubfield("2").setMqTag("source");
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
