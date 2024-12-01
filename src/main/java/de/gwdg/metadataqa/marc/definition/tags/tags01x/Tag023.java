package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Cluster ISSN
 * https://www.loc.gov/marc/bibliographic/bd023.html
 */
public class Tag023 extends DataFieldDefinition {

  private static Tag023 uniqueInstance;

  private Tag023() {
    initialize();
    postCreation();
  }

  public static Tag023 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag023();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "023";
    label = "Cluster ISSN";
    mqTag = "ClusterIssn";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd020.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Type of Cluster ISSN")
      .setCodes(
        "0", "ISSN-L",
        "1", "ISSN-H"
      )
      .setMqTag("type");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Cluster ISSN", "NR",
      "y", "Incorrect Cluster ISSN", "R",
      "z", "Canceled Cluster ISSN", "R",
      "0", "Authority record control number or standard number", "NR",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    // TODO validation ISO 2108

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setValidator(ISSNValidator.getInstance());

    getSubfield("y")
      .setBibframeTag("acquisitionTerms")
      .setValidator(ISSNValidator.getInstance());

    getSubfield("z")
      .setBibframeTag("canceledClusterISSN")
      .setValidator(ISSNValidator.getInstance());

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source");
    // TODO
    // Code from: ISSN Manual, Annex 3: List of ISSN Centre codes [PDF, 199 KB].

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
