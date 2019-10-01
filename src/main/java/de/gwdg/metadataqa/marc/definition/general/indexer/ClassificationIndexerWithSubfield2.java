package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.*;

public class ClassificationIndexerWithSubfield2 implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    List<MarcSubfield> subfield2s = dataField.getSubfield("2");
    if (subfield2s == null || subfield2s.isEmpty())
      return indexEntries;

    String classification = subfield2s.get(0).getValue();
    String key = null;
    List<String> values = new ArrayList<>();
    for (MarcSubfield subfield : dataField.getSubfield("a")) {
      key = keyGenerator.forSubfield(subfield) + "_" + classification;
      values.add(subfield.resolve());
    }
    indexEntries.put(key, values);

    return indexEntries;
  }

  private static ClassificationIndexerWithSubfield2 uniqueInstance;

  private ClassificationIndexerWithSubfield2() {}

  public static ClassificationIndexerWithSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new ClassificationIndexerWithSubfield2();
    return uniqueInstance;
  }
}
