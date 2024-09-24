package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SchemaIndicatorExtractor implements FieldIndexer {

  protected abstract String getSchemaAbbreviation(String indicatorSchemaCode, DataField dataField);
  protected abstract String getIndicatorSchemaCode(DataField dataField);

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    String schemaCode = getIndicatorSchemaCode(dataField);

    String schemaAbbreviation = getSchemaAbbreviation(schemaCode, dataField);
    if (schemaAbbreviation == null) {
      return indexEntries;
    }

    SubjectIndexExtractor extractor = new SubjectIndexExtractor(dataField, keyGenerator, schemaAbbreviation);
    extractor.extract();

    if (extractor.hadSuccess()) {
      indexEntries.put(extractor.getKey(), extractor.getValues());
    }

    return indexEntries;
  }
}
