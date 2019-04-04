package de.gwdg.metadataqa.marc.definition.general;

public enum LinkType {
  Action("a", "Action"),
  ConstituentItem("c", "Constituent item"),
  MetadataProvenance("p", "Metadata provenance"),
  Reproduction("r", "Reproduction"),
  GeneralLinking("u", "General linking, type unspecified"),
  GeneralSequencing("x", "General sequencing");

  private String code;
  private String label;

  LinkType(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public static LinkType byCode(String code) {
    for (LinkType type : values())
      if (type.code.equals(code))
        return type;
    return null;
  }
}
