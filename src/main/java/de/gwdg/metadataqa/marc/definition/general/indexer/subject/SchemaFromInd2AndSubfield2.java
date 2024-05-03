package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;

import java.util.List;

public class SchemaFromInd2AndSubfield2 extends SchemaIndicatorExtractor {

  @Override
  protected String getSchemaAbbreviation(String indicatorSchemaCode, DataField dataField) {
    // Checking for the code 7 is the only correct approach, but it seems that the following is used as well:
    // schemaCode.equals(" ")
    if (indicatorSchemaCode.equals("7")) {
      List<MarcSubfield> subfield2s = dataField.getSubfield("2");
      if (subfield2s == null || subfield2s.isEmpty()) {
        return null;
      }

      return subfield2s.get(0).getValue();
    }

    // Try to resolve the schema abbreviation from the indicator 1
    // If successful, return the resolved schema abbreviation. Otherwise, return that schema abbreviation from
    // the indicator 1.
    try {
      return ClassificationSchemes.getInstance().resolve(dataField.resolveInd2());
    } catch (IllegalArgumentException e) {
      return indicatorSchemaCode.equals(" ") ? "" : indicatorSchemaCode;
    }
  }

  @Override
  protected String getIndicatorSchemaCode(DataField dataField) {
    return dataField.getInd2();
  }

  private static SchemaFromInd2AndSubfield2 uniqueInstance;

  private SchemaFromInd2AndSubfield2() {}

  public static SchemaFromInd2AndSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd2AndSubfield2();
    return uniqueInstance;
  }
}
