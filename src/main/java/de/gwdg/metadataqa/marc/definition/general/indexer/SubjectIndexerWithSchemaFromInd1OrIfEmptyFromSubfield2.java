package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2 extends SubjectIndexer implements FieldIndexer {

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
      schemaAbbreviation = ClassificationSchemes.getInstance().resolve(dataField.resolveInd1());
    }

    KeyValuesExtractor extractor = new KeyValuesExtractor(dataField, keyGenerator, schemaAbbreviation).invoke();
    if (extractor.hadSuccess())
      indexEntries.put(extractor.getKey(), extractor.getValues());

    return indexEntries;
  }

  private static SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2 uniqueInstance;

  private SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2() {}

  public static SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2();
    return uniqueInstance;
  }
}
