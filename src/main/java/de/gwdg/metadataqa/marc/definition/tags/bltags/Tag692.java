package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Nineteenth Century Subject Series Field
 */
public class Tag692 extends DataFieldDefinition {

  private static Tag692 uniqueInstance;

  private Tag692() {
    initialize();
    postCreation();
  }

  public static Tag692 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag692();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "692";
    label = "Nineteenth Century Subject Series Field";
    mqTag = "collectionSubset";
    cardinality = Cardinality.Repeatable;
    obsolete = true;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "General Collection subject code", "NR",
      "b", "Linguistics collection code", "NR",
      "c", "Visual Arts and Architecture collection code", "NR",
      "e", "Publishing, the Book Trade & the Diffusion of Knowledge collection code", "NR",
      "f", "Women Writers collection code", "NR",
      "g", "Children’s Literature collection code", "NR",
      "i", "Nineteenth Century Books on China collection code", "NR",
      "p", "Year code", "NR"
    );

    getSubfield("a")
      .setCodes(
        "Agr", "Agriculture",
        "Eco", "Economics",
        "Edu", "Education",
        "Geo", "Geography and topography",
        "His", "History and archaeology",
        "HoM", "Household management",
        "Jur", "Jurisprudence",
        "Med", "Medicine",
        "Phi", "Philosophy",
        "Pol", "Politics",
        "Psy", "Psychology",
        "Rec", "Recreation",
        "Rel", "Religion",
        "Sci", "Science",
        "UsA", "Useful arts"
      )
      .setMqTag("general");

    getSubfield("b")
      .setCodes("L", "L")
      .setMqTag("linguistics");

    getSubfield("c")
      .setCodes("A", "A")
      .setMqTag("visualArtsAndArchitecture");

    getSubfield("e")
      .setCodes("B", "B")
      .setMqTag("publishing");

    getSubfield("f")
      .setCodes("W", "W")
      .setMqTag("womenWriters");

    getSubfield("g")
      .setCodes("C", "C")
      .setMqTag("children");

    getSubfield("i")
      .setCodes(
        "AS", "Anthropology & society",
        "CE", "Economics & commerce",
        "GS", "Geography",
        "HS", "History of China",
        "LA", "Literature & the arts",
        "PG", "Politics & government",
        "RP", "Religion and philosophy"
      )
      .setMqTag("onChina");

    getSubfield("p")
      .setValidator(new RegexValidator("^(x|\\d\\((\\d{2}/\\d{2}|\\d{2}-\\d{2})\\))$"))
      .setMqTag("year");
  }
}
