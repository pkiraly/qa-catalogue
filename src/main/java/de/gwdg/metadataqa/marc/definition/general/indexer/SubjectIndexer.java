package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.ArrayList;
import java.util.List;

abstract class SubjectIndexer {

  protected void addUniqueValues(List<String> values, MarcSubfield subfield) {
    String value = subfield.resolve();
    if (!values.contains(value)) {
      values.add(value);
    }
  }

  protected class KeyValuesExtractor {
    private DataField dataField;
    private DataFieldKeyGenerator keyGenerator;
    private String schemaAbbreviation;
    private String key;
    private List<String> values;
    private boolean success = false;

    public KeyValuesExtractor(DataField dataField, DataFieldKeyGenerator keyGenerator, String schemaAbbreviation) {
      this.dataField = dataField;
      this.keyGenerator = keyGenerator;
      this.schemaAbbreviation = schemaAbbreviation;
    }

    public String getKey() {
      return key;
    }

    public List<String> getValues() {
      return values;
    }

    public KeyValuesExtractor invoke() {
      List<MarcSubfield> subfields = dataField.getSubfield("a");
      success = false;
      if (subfields != null && subfields.isEmpty()) {
        key = keyGenerator.forSubfield(subfields.get(0)) + "_" + schemaAbbreviation;

        values = new ArrayList<>();
        for (MarcSubfield subfield : subfields) {
          addUniqueValues(values, subfield);
        }
        success = true;
      }

      return this;
    }

    public boolean hadSuccess() {
      return success;
    }
  }



}
