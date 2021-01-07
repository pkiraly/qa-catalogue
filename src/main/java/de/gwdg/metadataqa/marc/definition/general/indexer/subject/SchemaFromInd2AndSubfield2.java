package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaFromInd2AndSubfield2 extends SubjectIndexer implements FieldIndexer {

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    String schemaCode = dataField.getInd2();
    String schemaAbbreviation;
    if (schemaCode.equals("7")    // this is the only correct value
       // || schemaCode.equals(" ") // this is not correct, but it seems it is used
    ) {
      List<MarcSubfield> subfield2s = dataField.getSubfield("2");
      if (subfield2s == null || subfield2s.isEmpty())
        return indexEntries;

      schemaAbbreviation = subfield2s.get(0).getValue();
    } else {
      try {
        schemaAbbreviation = ClassificationSchemes.getInstance().resolve(dataField.resolveInd2());
      } catch (IllegalArgumentException e) {
        schemaAbbreviation = dataField.getInd2() == " " ? "" : dataField.getInd2();
      }
    }

    KeyValuesExtractor extractor = new KeyValuesExtractor(dataField, keyGenerator, schemaAbbreviation).invoke();
    if (extractor.hadSuccess())
      indexEntries.put(extractor.getKey(), extractor.getValues());

    return indexEntries;
  }

  private static SchemaFromInd2AndSubfield2 uniqueInstance;

  private SchemaFromInd2AndSubfield2() {}

  public static SchemaFromInd2AndSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd2AndSubfield2();
    return uniqueInstance;
  }
}
