package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Translation of Title by Cataloging Agency
 * http://www.loc.gov/marc/bibliographic/bd242.html
 */
public class Tag242 extends DataFieldDefinition {
  private static Tag242 uniqueInstance;

  private Tag242() {
    initialize();
    postCreation();
  }

  public static Tag242 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag242();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "242";
    label = "Translation of Title by Cataloging Agency";
    bibframeTag = "VariantTitle";
    mqTag = "TitleTranslation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd242.html";

    ind1 = new Indicator("Title added entry")
      .setCodes(
        "0", "No added entry",
        "1", "Added entry"
      )
      .setMqTag("titleAddedEntry")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Nonfiling characters")
      .setCodes(
        "0", "No nonfiling characters",
        "1-9", "Number of nonfiling characters"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind2.getCode("1-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Title", "NR",
      "b", "Remainder of title", "NR",
      "c", "Statement of responsibility, etc.", "NR",
      "h", "Medium", "NR",
      "n", "Number of part/section of a work", "R",
      "p", "Name of part/section of a work", "R",
      "y", "Language code of translated title", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("y").setCodeList(LanguageCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("b").setBibframeTag("subTitle");
    getSubfield("c").setMqTag("responsibility")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("h").setMqTag("medium")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);
    getSubfield("n").setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("p").setBibframeTag("partName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("y").setBibframeTag("language")
      .setFrbrFunctions(ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setHistoricalSubfields(
      "d", "Designation of section (BK, AM, MP, MU, VM, SE) [OBSOLETE, 1979]",
      "e", "Name of part/section (BK, AM, MP, MU, VM, SE) [OBSOLETE, 1979]"
    );
  }
}
