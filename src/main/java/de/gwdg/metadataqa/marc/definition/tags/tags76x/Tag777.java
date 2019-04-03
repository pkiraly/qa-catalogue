package de.gwdg.metadataqa.marc.definition.tags.tags76x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.Tag76xSubfield7PositionsGenerator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Issued With Entry
 * http://www.loc.gov/marc/bibliographic/bd777.html
 */
public class Tag777 extends DataFieldDefinition {

  private static Tag777 uniqueInstance;

  private Tag777() {
    initialize();
    postCreation();
  }

  public static Tag777 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag777();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "777";
    label = "Issued With Entry";
    bibframeTag = "IssuedWith";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd777.html";

    ind1 = new Indicator("Note controller")
      .setCodes(
        "0", "Display note",
        "1", "Do not display note"
      )
      .setMqTag("noteController")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Issued with",
        "8", "No display constant generated"
      )
      .setHistoricalCodes(
        "0", "Issued with [OBSOLETE] [CAN/MARC only]",
        "1", "With [OBSOLETE] [CAN/MARC only]",
        "2", "Bound with [OBSOLETE] [CAN/MARC only]"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Main entry heading", "NR",
      "b", "Edition", "NR",
      "c", "Qualifying information", "NR",
      "d", "Place, publisher, and date of publication", "NR",
      "g", "Related parts", "R",
      "h", "Physical description", "NR",
      "i", "Relationship information", "R",
      "k", "Series data for related item", "R",
      "m", "Material-specific details", "NR",
      "n", "Note", "R",
      "o", "Other item identifier", "R",
      "s", "Uniform title", "NR",
      "t", "Title", "NR",
      "w", "Record control number", "R",
      "x", "International Standard Serial Number", "NR",
      "y", "CODEN designation", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "7", "Control subfield", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("4").setCodeList(RelatorCodes.getInstance());
    // TODO: this requires position parser!
    // see http://www.loc.gov/marc/bibliographic/bd76x78x.html

    getSubfield("7").setPositions(Tag76xSubfield7PositionsGenerator.getPositions());
    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
    getSubfield("b").setBibframeTag("editionStatement")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("c").setBibframeTag("qualifier");
    getSubfield("d").setBibframeTag("provisionActivityStatement")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("g").setBibframeTag("part");
    getSubfield("h").setBibframeTag("extent")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("i").setBibframeTag("relation");
    getSubfield("k").setBibframeTag("seriesStatement")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("m").setBibframeTag("note").setMqTag("materialSpecificDetails");
    getSubfield("n").setBibframeTag("note");
    getSubfield("o").setMqTag("otherItemIdentifier")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("s").setBibframeTag("title").setMqTag("uniformTitle")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("t").setBibframeTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("w").setMqTag("recordControlNumber")
      .setFrbrFunctions(ManagementIdentify);
    getSubfield("x").setBibframeTag("issn")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("y").setBibframeTag("coden")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("4").setMqTag("relationship");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    /** TODO
     *  7/00  .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort)
     *  7/01  .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort)
     *  7/02  .setFrbrFunctions(ManagementProcess)
     *  7/03  .setFrbrFunctions(ManagementProcess)
     */
    getSubfield("7").setMqTag("controlSubfield");
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
