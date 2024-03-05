package de.gwdg.metadataqa.marc.utils.marcreader.schema;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.utils.marcreader.SchemaManager;

import java.util.Map;

public class Marc21SchemaManager implements SchemaManager {

  Map<String, DataFieldDefinition> schema;

  public Marc21SchemaManager(Map<String, DataFieldDefinition> schema) {
    this.schema = schema;
  }

  @Override
  public DataFieldDefinition lookup(String tag) {
    return schema.getOrDefault(tag, null);
  }

  @Override
  public int size() {
    return schema.size();
  }

}
