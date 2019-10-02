package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.*;

public class SubjectIndexerWithSchemaFromSubfield2 implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    List<MarcSubfield> subfield2s = dataField.getSubfield("2");
    if (subfield2s == null || subfield2s.isEmpty())
      return indexEntries;

    String schemaAbbreviation = subfield2s.get(0).getValue();
    String key = null;
    List<String> values = new ArrayList<>();
    for (MarcSubfield subfield : dataField.getSubfield("a")) {
      if (key == null)
        key = keyGenerator.forSubfield(subfield) + "_" + schemaAbbreviation;
      values.add(subfield.resolve());
    }
    indexEntries.put(key, values);

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
