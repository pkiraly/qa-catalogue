package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.*;

public class SchemaFromSubfield2 extends SubjectIndexer implements FieldIndexer {

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

  private static SchemaFromSubfield2 uniqueInstance;

  private SchemaFromSubfield2() {}

  public static SchemaFromSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromSubfield2();
    return uniqueInstance;
  }

}
