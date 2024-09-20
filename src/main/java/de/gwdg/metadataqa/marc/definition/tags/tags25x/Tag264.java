package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Production, Publication, Distribution, Manufacture, and Copyright Notice
 * https://www.loc.gov/marc/bibliographic/bd264.html
 */
public class Tag264 extends DataFieldDefinition {

  private static Tag264 uniqueInstance;

  private Tag264() {
    initialize();
    postCreation();
  }

  public static Tag264 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag264();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "264";
    label = "Production, Publication, Distribution, Manufacture, and Copyright Notice";
    mqTag = "ProvisionActivity";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd264.html";

    ind1 = new Indicator("Sequence of statements")
      .setCodes(
        " ", "Not applicable/No information provided/Earliest",
        "2", "Intervening",
        "3", "Current/Latest"
      )
      .setMqTag("sequenceOfStatements");

    ind2 = new Indicator("Function of entity")
      .setCodes(
        "0", "Production",
        "1", "Publication",
        "2", "Distribution",
        "3", "Manufacture",
        "4", "Copyright notice date"
      )
      .setMqTag("function");

    setSubfieldsWithCardinality(
      "a", "Place of production, publication, distribution, manufacture", "R",
      "b", "Name of producer, publisher, distributor, manufacturer", "R",
      "c", "Date of production, publication, distribution, manufacture, or copyright notice", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("place");

    getSubfield("b")
      .setBibframeTag("agent");

    getSubfield("c")
      .setBibframeTag("date");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setMqTag("linkage");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    putVersionSpecificSubfields(MarcVersion.HBZ, Arrays.asList(
      new SubfieldDefinition("9", "Feldzuordnung Aleph", "R")
    ));
  }
}
