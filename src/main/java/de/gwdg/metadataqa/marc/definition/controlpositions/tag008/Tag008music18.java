package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Form of composition
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music18 extends ControlfieldPositionDefinition {
  private static Tag008music18 uniqueInstance;

  private Tag008music18() {
    initialize();
    extractValidCodes();
  }

  public static Tag008music18 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008music18();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of composition";
    id = "008music18";
    mqTag = "formOfComposition";
    positionStart = 18;
    positionEnd = 20;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
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