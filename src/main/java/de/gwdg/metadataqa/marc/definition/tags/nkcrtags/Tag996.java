package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * CPK holdings information
 * https://github.com/moravianlibrary/CPK/wiki/Pole-996
 */
public class Tag996 extends DataFieldDefinition {

  private static Tag996 uniqueInstance;

  private Tag996() {
    initialize();
    postCreation();
  }

  public static Tag996 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag996();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "996";
    label = "CPK holdings information";
    mqTag = "CPKHoldingsInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://github.com/moravianlibrary/CPK/wiki/Pole-996";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b", "čárový kód", "NR",
      "c", "skladová signatura", "NR",
      "h", "signatura volného výběr", "NR",
      "d", "rozpis svazku", "NR",
      "v", "číslo ročníku", "NR",
      "i", "číslo", "NR",
      "y", "rok", "NR",
      "l", "pobočka (dílčí knihovna)", "NR",
      "r", "sbírka", "NR",
      "s", "status", "NR",
      "n", "počet výpůjček jednotky", "NR",
      "a", "dostupnost v hodinách", "NR",
      "e", "sigla", "NR",
      "q", "nezobrazovat", "NR",
      "w", "administrativní číslo zázn.", "NR",
      "u", "z30_item_sequence (Aleph)", "NR",
      "j", "kód administrativní báze (Alpeh)", "NR"
    );

    getSubfield("s").setCodes(
      "A", "absenčně",
      "P", "prezenčně",
      "D", "omezená dostupnost",
      "N", "dočasně nedostupné",
      "AR", "nedostupné",
      "ZP", "nedostupné",
      "Z", "nedostupné",
      "V", "nedostupné",
      "PK", "nedostupné",
      "RZ", "nedostupné"
    );
  }
}