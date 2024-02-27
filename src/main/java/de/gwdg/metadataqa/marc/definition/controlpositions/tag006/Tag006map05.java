package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Projection
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map05 extends ControlfieldPositionDefinition {
  private static Tag006map05 uniqueInstance;

  private Tag006map05() {
    initialize();
    extractValidCodes();
  }

  public static Tag006map05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006map05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Projection";
    id = "006map05";
    mqTag = "projection";
    positionStart = 5;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}