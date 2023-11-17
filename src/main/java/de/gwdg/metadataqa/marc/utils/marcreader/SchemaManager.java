package de.gwdg.metadataqa.marc.utils.marcreader;

import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;

public interface SchemaManager {
  BibliographicFieldDefinition lookup(String tag);
  int size();
}
