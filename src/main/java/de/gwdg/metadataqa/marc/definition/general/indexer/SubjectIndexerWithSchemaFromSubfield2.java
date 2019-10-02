package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.*;

public class SubjectIndexerWithSchemaFromSubfield2 extends SubjectIndexer implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    List<MarcSubfield> subfield2s = dataField.getSubfield("2");
    if (subfield2s == null || subfield2s.isEmpty())
      return indexEntries;

    String schemaAbbreviation = subfield2s.get(0).getValue();

    KeyValuesExtractor extractor = new KeyValuesExtractor(dataField, keyGenerator, schemaAbbreviation).invoke();
    if (extractor.hadSuccess())
      indexEntries.put(extractor.getKey(), extractor.getValues());

    return indexEntries;
  }

  private static SubjectIndexerWithSchemaFromSubfield2 uniqueInstance;

  private SubjectIndexerWithSchemaFromSubfield2() {}

  public static SubjectIndexerWithSchemaFromSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SubjectIndexerWithSchemaFromSubfield2();
    return uniqueInstance;
  }

}
