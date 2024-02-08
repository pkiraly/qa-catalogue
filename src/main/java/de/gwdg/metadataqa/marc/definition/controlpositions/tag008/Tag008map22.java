package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Projection
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map22 extends ControlfieldPositionDefinition {
  private static Tag008map22 uniqueInstance;

  private Tag008map22() {
    initialize();

  }

  public static Tag008map22 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008map22();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Projection";
    id = "008map22";
    mqTag = "projection";
    positionStart = 22;
    positionEnd = 24;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
    codes = Utils.generateCodes(
      "  ", "Projection not specified",
      "aa", "Aitoff",
      "ab", "Gnomic",
      "ac", "Lambert's azimuthal equal area",
      "ad", "Orthographic",
      "ae", "Azimuthal equidistant",
      "af", "Stereographic",
      "ag", "General vertical near-sided",
      "am", "Modified stereographic for Alaska",
      "an", "Chamberlin trimetric",
      "ap", "Polar stereographic",
      "au", "Azimuthal, specific type unknown",
      "az", "Azimuthal, other",
      "ba", "Gall",
      "bb", "Goode's homolographic",
      "bc", "Lambert's cylindrical equal area",
      "bd", "Mercator",
      "be", "Miller",
      "bf", "Mollweide",
      "bg", "Sinusoidal",
      "bh", "Transverse Mercator",
      "bi", "Gauss-Kruger",
      "bj", "Equirectangular",
      "bk", "Krovak",
      "bl", "Cassini-Soldner",
      "bo", "Oblique Mercator",
      "br", "Robinson",
      "bs", "Space oblique Mercator",
      "bu", "Cylindrical, specific type unknown",
      "bz", "Cylindrical, other",
      "ca", "Albers equal area",
      "cb", "Bonne",
      "cc", "Lambert's conformal conic",
      "ce", "Equidistant conic",
      "cp", "Polyconic",
      "cu", "Conic, specific type unknown",
      "cz", "Conic, other",
      "da", "Armadillo",
      "db", "Butterfly",
      "dc", "Eckert",
      "dd", "Goode's homolosine",
      "de", "Miller's bipolar oblique conformal conic",
      "df", "Van Der Grinten",
      "dg", "Dimaxion",
      "dh", "Cordiform",
      "dl", "Lambert conformal",
      "zz", "Other",
      "||", "No attempt to code"
    );
  }
}