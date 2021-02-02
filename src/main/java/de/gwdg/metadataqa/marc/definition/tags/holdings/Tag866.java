package de.gwdg.metadataqa.marc.definition.tags.holdings;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.HoldingSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Holding Institution
 * http://www.loc.gov/marc/holdings/hd866.html
 */
public class Tag866 extends DataFieldDefinition {

  private static Tag866 uniqueInstance;

  private Tag866() {
    initialize();
    postCreation();
  }

  public static Tag866 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag866();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "866";
    label = "Textual Holdings-Basic Bibliographic Unit";
    mqTag = "TextualHoldingsBasicBibliographicUnit";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/hd866.html";

    ind1 = new Indicator("Field encoding level")
      .setCodes(
        " ", "No information provided",
        "3", "Holdings level 3",
        "4", "Holdings level 4",
        "5", "Holdings level 4 with piece designation"
      )
      .setMqTag("fieldEncodingLevel")
      .setFrbrFunctions(ManagementProcess);

    ind2 = new Indicator("Type of notation")
      .setCodes(
        "0", "Non-standard",
        "1", "ANSI/NISO Z39.71 or ISO 10324",
        "2", "ANSI Z39.42",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("notationType")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Textual holdings", "NR",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "2", "Source of notation", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(HoldingSchemeSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
