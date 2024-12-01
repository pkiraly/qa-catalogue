package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * International Standard Serial Number
 * https://www.loc.gov/marc/bibliographic/bd022.html
 */
public class Tag022 extends DataFieldDefinition {

  private static Tag022 uniqueInstance;

  private Tag022() {
    initialize();
    postCreation();
  }

  public static Tag022 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag022();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "022";
    label = "International Standard Serial Number";
    bibframeTag = "Issn";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd022.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Level of international interest")
      .setCodes(
        " ", "No level specified",
        "0", "Continuing resource of international interest",
        "1", "Continuing resource not of international interest"
      )
      .setMqTag("levelOfInternationalInterest")
      .setFrbrFunctions(DiscoverySelect, ManagementProcess);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "International Standard Serial Number", "NR",
      // "l", "ISSN-L", "NR",
      // "m", "Canceled ISSN-L", "R",
      "y", "Incorrect ISSN", "R",
      "z", "Canceled ISSN", "R",
      "0", "Authority record control number or standard number", "NR",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    // TODO check against ISSN National Centres code list http://www.issn.org/
    // getSubfield("2").setCodeList();
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: what about the rest of the fields?
    getSubfield("a").setValidator(ISSNValidator.getInstance());


    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    /* Obsolete
    getSubfield("l")
      .setBibframeTag("issnL")
      .setCompilanceLevels("A", "A");

    getSubfield("m")
      .setMqTag("canceledIssnL")
      .setCompilanceLevels("A", "A");
     */

    getSubfield("y")
      .setMqTag("incorrect")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("z")
      .setMqTag("canceled")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "b", "Form of issue [OBSOLETE] [CAN/MARC only]",
      "c", "Price [OBSOLETE] [CAN/MARC only]",
      "l", "ISSN-L [OBSOLETE, 2023]. Subfield $l was made obsolete in favor of recording ISSN-L in field 023.",
      "m", "Canceled ISSN-L [OBSOLETE, 2023] Subfield $m was made obsolete in favor of recording canceled ISSN-L in field 023."
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
