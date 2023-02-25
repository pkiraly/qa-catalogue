package de.gwdg.metadataqa.marc.definition.bibliographic;

import de.gwdg.metadataqa.marc.definition.Cardinality;

public interface BibliographicFieldDefinition {
  String getTag();
  Cardinality getCardinality();
  String getLabel();
  String getDescriptionUrl();
}
