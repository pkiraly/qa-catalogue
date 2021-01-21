package de.gwdg.metadataqa.marc.definition.general;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;
import java.util.List;

public class Tag76xSubfield7PositionsGenerator {

  public static List<ControlfieldPositionDefinition> getPositions() {

    return Arrays.asList(
      new ControlfieldPositionDefinition("Type of main entry heading", 0, 1)
        .setCodes(Utils.generateCodes(
          "p", "Personal name",
          "c", "Corporate name",
          "m", "Meeting name",
          "u", "Uniform title",
          "n", "Not applicable"
        ))
        .setMqTag("typeOfHeading"),

      new ControlfieldPositionDefinition("Form of name", 1, 2)
        .setCodes(Utils.generateCodes(
          "0", "Forename",
          "1", "Surname",
          "3", "Family name",

          "0", "Inverted name",
          "1", "Jurisdiction name",
          "2", "Name in direct order",

          "n", "Not applicable"
        ))
        .setMqTag("formOfName"),

      new ControlfieldPositionDefinition("Type of record", 2, 3)
        .setCodes(Utils.generateCodes(
          "a", "Language material",
          "c", "Notated music",
          "d", "Manuscript notated music",
          "e", "Cartographic material",
          "f", "Manuscript cartographic material",
          "g", "Projected medium",
          "i", "Nonmusical sound recording",
          "j", "Musical sound recording",
          "k", "Two-dimensional nonprojectable graphic",
          "m", "Computer file",
          "o", "Kit",
          "p", "Mixed material",
          "r", "Three-dimensional artifact or naturally occurring object",
          "t", "Manuscript language material"
        )).setMqTag("typeOfRecord"),

      // Bibliographic level from Leader/07 of related record
      new ControlfieldPositionDefinition("Bibliographic level", 3, 4)
        .setCodes(Utils.generateCodes(
          "a", "Monographic component part",
          "b", "Serial component part",
          "c", "Collection",
          "d", "Subunit",
          "i", "Integrating resource",
          "m", "Monograph/item",
          "s", "Serial"
        )).setMqTag("bibliographicLevel")
    );
  }
}
