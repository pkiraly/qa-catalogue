package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

import java.util.List;
import java.util.Map;

public class SubjectIndexerTest {

  protected Map<String, List<String>> getIndexEntries(DataField field) {
    FieldIndexer indexer = field.getDefinition().getFieldIndexer();
    return indexer.index(field, field.getKeyGenerator(SolrFieldType.MIXED));
  }

}
