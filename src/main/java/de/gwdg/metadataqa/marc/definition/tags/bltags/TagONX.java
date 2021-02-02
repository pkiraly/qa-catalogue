package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;

/**
 * ONIX Subjects
 */
public class TagONX extends DataFieldDefinition {

  private static TagONX uniqueInstance;

  private TagONX() {
    initialize();
    postCreation();
  }

  public static TagONX getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagONX();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "ONX";
    label = "ONIX Un-Mapped Data";
    mqTag = "OnixUnmappedData";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Country of publication code", "NR",
      "b", "Language code", "NR",
      "c", "Edition type code", "NR"
    );

    // TODO: the example use capital letters, the country code list contains small letters
    getSubfield("a")
      // .setCodeList(CountryCodes.getInstance())
      .setMqTag("country");

    getSubfield("b")
      .setCodeList(LanguageCodes.getInstance())
      .setMqTag("language");

    // https://www.medra.org/stdoc/onix-codelist-21.htm
    getSubfield("c")
      .setCodes(
        "ABR", "Abridged",
        "ADP", "Adapted",
        "ALT", "Alternate",
        "ANN", "Annotated",
        "BLL", "Bilingual edition",
        "BRL", "Braille edition",
        "CMB", "Combined volume",
        "CRI", "Critical",
        "CSP", "Coursepack",
        "DGO", "Digital original",
        "ENL", "Enlarged",
        "EXP", "Expurgated",
        "FAC", "Facsimile",
        "FST", "Festschrift",
        "ILL", "Illustrated",
        "LTE", "Large type / large print",
        "MCP", "Microprint",
        "MDT", "Media tie-in",
        "MLL", "Multilingual edition",
        "NED", "New edition",
        "NUM", "Edition with numbered copies",
        "PRB", "Prebound edition",
        "REV", "Revised",
        "SCH", "School edition",
        "SMP", "Simplified language edition",
        "SPE", "Special edition",
        "STU", "Student edition",
        "TCH", "Teacher’s edition",
        "UBR", "Unabridged",
        "ULP", "Ultra large print",
        "UXP", "Unexpurgated",
        "VAR", "Variorum"
      )
      .setMqTag("editionType");
  }
}
