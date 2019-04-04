package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of composition
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music01 extends ControlSubfieldDefinition {
  private static Tag006music01 uniqueInstance;

  private Tag006music01() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of composition";
    id = "tag006music01";
    mqTag = "formOfComposition";
    positionStart = 1;
    positionEnd = 3;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "an", "Anthems",
      "bd", "Ballads",
      "bg", "Bluegrass music",
      "bl", "Blues",
      "bt", "Ballets",
      "ca", "Chaconnes",
      "cb", "Chants, Other religions",
      "cc", "Chant, Christian",
      "cg", "Concerti grossi",
      "ch", "Chorales",
      "cl", "Chorale preludes",
      "cn", "Canons and rounds",
      "co", "Concertos",
      "cp", "Chansons, polyphonic",
      "cr", "Carols",
      "cs", "Chance compositions",
      "ct", "Cantatas",
      "cy", "Country music",
      "cz", "Canzonas",
      "df", "Dance forms",
      "dv", "Divertimentos, serenades, cassations, divertissements, and notturni",
      "fg", "Fugues",
      "fl", "Flamenco",
      "fm", "Folk music",
      "ft", "Fantasias",
      "gm", "Gospel music",
      "hy", "Hymns",
      "jz", "Jazz",
      "mc", "Musical revues and comedies",
      "md", "Madrigals",
      "mi", "Minuets",
      "mo", "Motets",
      "mp", "Motion picture music",
      "mr", "Marches",
      "ms", "Masses",
      "mu", "Multiple forms",
      "mz", "Mazurkas",
      "nc", "Nocturnes",
      "nn", "Not applicable",
      "op", "Operas",
      "or", "Oratorios",
      "ov", "Overtures",
      "pg", "Program music",
      "pm", "Passion music",
      "po", "Polonaises",
      "pp", "Popular music",
      "pr", "Preludes",
      "ps", "Passacaglias",
      "pt", "Part-songs",
      "pv", "Pavans",
      "rc", "Rock music",
      "rd", "Rondos",
      "rg", "Ragtime music",
      "ri", "Ricercars",
      "rp", "Rhapsodies",
      "rq", "Requiems",
      "sd", "Square dance music",
      "sg", "Songs",
      "sn", "Sonatas",
      "sp", "Symphonic poems",
      "st", "Studies and exercises",
      "su", "Suites",
      "sy", "Symphonies",
      "tc", "Toccatas",
      "tl", "Teatro lirico",
      "ts", "Trio-sonatas",
      "uu", "Unknown",
      "vi", "Villancicos",
      "vr", "Variations",
      "wz", "Waltzes",
      "za", "Zarzuelas",
      "zz", "Other",
      "||", "No attempt to code"
    );
  }
}