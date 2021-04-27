package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaFromInd1OrIfEmptyFromSubfield2 extends SubjectIndexer implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    String schemaCode = dataField.getInd1();
    String schemaAbbreviation;
    if (schemaCode.equals(" ")) {
      List<MarcSubfield> subfield2s = dataField.getSubfield("2");
      if (subfield2s == null || subfield2s.isEmpty())
        return indexEntries;

      schemaAbbreviation = subfield2s.get(0).getValue();
    } else {
      try {
        schemaAbbreviation = ClassificationSchemes.getInstance().resolve(dataField.resolveInd1());
      } catch (IllegalArgumentException e) {
        schemaAbbreviation = dataField.getInd1() == " " ? "" : dataField.getInd1();
      }
    }

    KeyValuesExtractor extractor = new KeyValuesExtractor(dataField, keyGenerator, schemaAbbreviation).invoke();
    if (extractor.hadSuccess())
      indexEntries.put(extractor.getKey(), extractor.getValues());

    return indexEntries;
  }

  private static SchemaFromInd1OrIfEmptyFromSubfield2 uniqueInstance;

  private SchemaFromInd1OrIfEmptyFromSubfield2() {}

  public static SchemaFromInd1OrIfEmptyFromSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd1OrIfEmptyFromSubfield2();
    return uniqueInstance;
  }
}
