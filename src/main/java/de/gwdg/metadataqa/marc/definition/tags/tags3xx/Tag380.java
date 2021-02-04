package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Form of Work
 * http://www.loc.gov/marc/bibliographic/bd380.html
 */
public class Tag380 extends DataFieldDefinition {
  private static Tag380 uniqueInstance;

  private Tag380() {
    initialize();
    postCreation();
  }

  public static Tag380 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag380();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "380";
    label = "Form of Work";
    bibframeTag = "GenreForm";
    mqTag = "FormOfWork";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd380.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Form of work", "R",
      "0", "Record control number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setCompilanceLevels("M");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance())
      .setCompilanceLevels("A");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCompilanceLevels("A");


    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");
  }
}
