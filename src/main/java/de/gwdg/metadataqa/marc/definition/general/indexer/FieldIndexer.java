package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.List;
import java.util.Map;

public interface FieldIndexer {
  Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator);
}
