package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaFromInd2 extends SubjectIndexer implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    String schemaAbbreviation;
    try {
      schemaAbbreviation = ClassificationSchemes.getInstance().resolve(dataField.resolveInd2());
    } catch (IllegalArgumentException e) {
      schemaAbbreviation = (dataField.getInd2() == " ") ? "" : dataField.getInd2();
    }

    KeyValuesExtractor extractor = new KeyValuesExtractor(dataField, keyGenerator, schemaAbbreviation).invoke();
    if (extractor.hadSuccess())
      indexEntries.put(extractor.getKey(), extractor.getValues());

    return indexEntries;
  }

  private static SchemaFromInd2 uniqueInstance;

  private SchemaFromInd2() {}

  public static SchemaFromInd2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd2();
    return uniqueInstance;
  }
}
