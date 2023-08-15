package de.gwdg.metadataqa.marc.definition.controltype;

public enum Control007Category implements ControlType {
  COMMON              ("common", "Common",          "common"),
  MAP                 ("a", "Map",                  "map"),
  ELECTRONIC_RESOURCE ("c", "Electronic resource",  "electro"),
  GLOBE               ("d", "Globe",                "globe"),
  TACTILE_MATERIAL    ("f", "Tactile material",     "tactile"),
  PROJECTED_GRAPHIC   ("g", "Projected graphic",    "projected"),
  MICROFORM           ("h", "Microform",            "microform"),
  NONPROJECTED_GRAPHIC("k", "Nonprojected graphic", "nonprojected"),
  MOTION_PICTURE      ("m", "Motion picture",       "motionPicture"),
  KIT                 ("o", "Kit",                  "kit"),
  NOTATED_MUSIC       ("q", "Notated music",        "music"),
  REMOTE_SENSING_IMAGE("r", "Remote-sensing image", "remoteSensing"),
  SOUND_RECORDING     ("s", "Sound recording",      "soundRecording"),
  TEXT                ("t", "Text",                 "text"),
  VIDEO_RECORDING     ("v", "Videorecording",       "video"),
  UNSPECIFIED         ("z", "Unspecified",          "unspecified");

  String code;
  String label;
  String abbreviation;

  Control007Category(String code, String label, String abbreviation) {
    this.code = code;
    this.label = label;
    this.abbreviation = abbreviation;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return label;
  }

  public static Control007Category byCode(String code) {
    for (Control007Category category : values())
      if (category.code.equals(code))
        return category;
    return null;
  }

  public static Control007Category byLabel(String label) {
    for (Control007Category category : values())
      if (category.label.equals(label))
        return category;
    return null;
  }

  public static ControlType byAbbreviation(String abbreviation) {
    for (Control007Category category : values())
      if (category.abbreviation.equals(abbreviation))
        return category;
    return null;
  }

}
