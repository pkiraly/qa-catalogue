package de.gwdg.metadataqa.marc.analysis.bl;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class Element {
  String tag;
  String subfield;
  DataFieldDefinition definition;

  public Element(String tag, DataFieldDefinition definition, String subfield) {
    this.tag = tag;
    this.definition = definition;
    this.subfield = subfield;
  }

  public String getTag() {
    return tag;
  }

  public DataFieldDefinition getDefinition() {
    return definition;
  }

  public String getSubfield() {
    return subfield;
  }

  @Override
  public String toString() {
    return tag + (subfield != null ? "$" + subfield : "");
  }
}
