package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class SimpleControlField extends MarcControlField {

  public SimpleControlField(DataFieldDefinition definition, String content) {
    super(definition, content);
  }
}
