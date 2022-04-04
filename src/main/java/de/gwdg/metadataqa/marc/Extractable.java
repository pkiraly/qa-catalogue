package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

import java.util.List;
import java.util.Map;

public interface Extractable {
  Map<String, List<String>> getKeyValuePairs();
  Map<String, List<String>> getKeyValuePairs(SolrFieldType type);
  Map<String, List<String>> getKeyValuePairs(SolrFieldType type, MarcVersion marcVersion);
}
