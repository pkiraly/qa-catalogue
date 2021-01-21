package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag595 extends DataFieldDefinition {

  private static Tag595 uniqueInstance;

  private Tag595() {
    initialize();
    postCreation();
  }

  public static Tag595 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag595();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "595";
    label = "Megjegyzés a kötésről";
    mqTag = "BindingNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/595.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "A kötés anyaga", "NR",
      "b", "A kötés díszítése", "R",
      "c", "A kötés egyéb jellemzõi", "R",
      "d", "A kötés ideje", "NR",
      "i", "A megjegyzést bevezetõ kifejezés", "NR",
      "n", "Megjegyzés", "NR",
      "5", "Az intézmény és példány, amelyre a megjegyzés vonatkozik", "NR"
    );
  }
}
