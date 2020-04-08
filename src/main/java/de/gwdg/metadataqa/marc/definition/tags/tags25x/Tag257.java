package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Country of Producing Entity http://www.loc.gov/marc/bibliographic/bd257.html
 */
public class Tag257 extends DataFieldDefinition {
  private static Tag257 uniqueInstance;

  private Tag257() {
    initialize();
    postCreation();
  }

  public static Tag257 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag257();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "257";
    label = "Country of Producing Entity";
    mqTag = "Country";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd257.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Country of producing entity", "R", "0",
        "Authority record control number or standard number", "R", "2", "Source", "NR", "6", "Linkage", "NR", "8",
        "Field link and sequence number", "R");

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("Place").setMqTag("rdf:value")
        .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain).setCompilanceLevels("M", "M");

    getSubfield("0").setMqTag("authorityRecordControlNumber");

    getSubfield("2").setMqTag("source").setCompilanceLevels("A", "A");

    getSubfield("6").setBibframeTag("linkage").setFrbrFunctions(ManagementIdentify, ManagementProcess)
        .setCompilanceLevels("A", "A");

    getSubfield("8").setMqTag("fieldLink").setFrbrFunctions(ManagementIdentify, ManagementProcess)
        .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR,
        Arrays.asList(new SubfieldDefinition("7", "NKCR Authority ID", "NR")));
  }
}
