package de.gwdg.metadataqa.marc.definition.controltype;

public enum Control008Type implements ControlType {

  ALL_MATERIALS       ("All Materials",        "all"),
  BOOKS               ("Books",                "book"),
  CONTINUING_RESOURCES("Continuing Resources", "continuing"),
  MUSIC               ("Music",                "music"),
  MAPS                ("Maps",                 "map"),
  VISUAL_MATERIALS    ("Visual Materials",     "visual"),
  COMPUTER_FILES      ("Computer Files",       "computer"),
  MIXED_MATERIALS     ("Mixed Materials",      "mixed");

  String value;
  String abbreviation;

  Control008Type(String value, String abbreviation) {
    this.value = value;
    this.abbreviation = abbreviation;
  }

  public String getValue() {
    return value;
  }

  public static Control008Type byCode(String value) {
    for (Control008Type type : values())
      if (type.value.equals(value))
        return type;
    return null;
  }

  public static ControlType byAbbreviation(String abbreviation) {
    for (Control008Type category : values())
      if (category.abbreviation.equals(abbreviation))
        return category;
    return null;
  }
}
