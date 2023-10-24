package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Representative Expression Characteristics
 * http://www.loc.gov/marc/bibliographic/bd386.html
 */
public class Tag387 extends DataFieldDefinition {

  private static Tag387 uniqueInstance;

  private Tag387() {
    initialize();
    postCreation();
  }

  public static Tag387 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag387();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "387";
    label = "Representative Expression Characteristics";
    mqTag = "RepresentativeExpressionCharacteristics";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd386.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Aspect ratio of representative expression", "R",
      "b", "Color content of representative expression", "R",
      "c", "Content type of representative expression", "R",
      "d", "Date of capture of representative expression", "R",
      "e", "Date of representative expression", "R",
      "f", "Duration of representative expression", "R",
      "g", "Intended audience of representative expression", "R",
      "h", "Language of representative expression", "R",
      "i", "Place of capture of representative expression", "R",
      "j", "Projection of cartographic content of representative expression", "R",
      "k", "Scale of representative expression", "R",
      "l", "Script of representative expression", "R",
      "m", "Sound content of representative expression", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("aspectRatio");

    getSubfield("b")
      .setMqTag("colorContent");

    getSubfield("c")
      .setMqTag("contentType");

    getSubfield("d")
      .setMqTag("dateOfCapture");

    getSubfield("e")
      .setMqTag("dateOfExpression");

    getSubfield("f")
      .setMqTag("duration");

    getSubfield("g")
      .setMqTag("audience");

    getSubfield("h")
      .setMqTag("language");

    getSubfield("i")
      .setMqTag("place");

    getSubfield("j")
      .setMqTag("projection");

    getSubfield("k")
      .setMqTag("scale");

    getSubfield("l")
      .setMqTag("script");

    getSubfield("m")
      .setMqTag("soundContent");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
