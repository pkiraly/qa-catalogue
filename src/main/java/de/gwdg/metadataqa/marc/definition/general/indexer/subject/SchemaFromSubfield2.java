package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaFromSubfield2 implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    List<MarcSubfield> subfield2s = dataField.getSubfield("2");

    // If no subfields $2 are found, return the empty indexEntries
    if (subfield2s == null || subfield2s.isEmpty())
      return indexEntries;

    // Get the source from the first subfield $2
    String schemaAbbreviation = subfield2s.get(0).getValue();

    SubjectIndexExtractor extractor = new SubjectIndexExtractor(dataField, keyGenerator, schemaAbbreviation);
    extractor.extract();

    if (extractor.hadSuccess()) {
      indexEntries.put(extractor.getKey(), extractor.getValues());
    }

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
