package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag983 extends DataFieldDefinition {

  private static Tag983 uniqueInstance;

  private Tag983() {
    initialize();
    postCreation();
  }

  public static Tag983 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag983();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "983";
    label = "Konverziós adatok (Medea + Globaldigit)";
    mqTag = "KonverziosAdatokMedea";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:983";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "A retrokonverziót végző cég megjegyzése", "R",
      "b", "A retrokonverziót végző cég megjegyzése", "NR",
      "u", "Digitalizált cédula elérése (URL)", "NR",
      "x", "A retrokonverziót végző cég által használt kódolás", "R",
      "y", "A katalóguskarton szkennelt verziójának sorszáma", "R"
    );
  }
}
