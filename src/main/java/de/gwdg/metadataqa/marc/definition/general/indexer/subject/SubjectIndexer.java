package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.ClassificationAnalysis;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

abstract class SubjectIndexer {

  private static final Logger logger = Logger.getLogger(ClassificationAnalysis.class.getCanonicalName());

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

    public KeyValuesExtractor(DataField dataField,
                              DataFieldKeyGenerator keyGenerator,
                              String schemaAbbreviation) {
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
      if (subfields == null || subfields.isEmpty()) {
        success = false;
        if (!dataField.getDefinition().getTag().equals("852"))
          logger.warning("No subfield $a in the field: " + dataField.toString());
      } else {
        key = keyGenerator.forSubfield(subfields.get(0)) + "_" + Utils.solarize(schemaAbbreviation);

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
