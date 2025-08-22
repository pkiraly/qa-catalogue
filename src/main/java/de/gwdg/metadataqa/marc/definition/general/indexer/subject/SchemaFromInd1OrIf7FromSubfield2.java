package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;

import java.util.List;

public class SchemaFromInd1OrIf7FromSubfield2 extends SchemaIndicatorExtractor {

  @Override
  protected String getSchemaAbbreviation(String indicatorSchemaCode, DataField dataField) {
    if (indicatorSchemaCode.equals("7")) {
      List<MarcSubfield> subfield2s = dataField.getSubfield("2");
      if (subfield2s == null || subfield2s.isEmpty()) {
        return null;
      }

      return subfield2s.get(0).getValue();
    }

    try {
      return ClassificationSchemes.getInstance().resolve(dataField.resolveInd1());
    } catch (IllegalArgumentException e) {
      return indicatorSchemaCode.equals(" ") ? "" : indicatorSchemaCode;
    }
  }

  @Override
  protected String getIndicatorSchemaCode(DataField dataField) {
    return dataField.getInd1();
  }

  private static SchemaFromInd1OrIf7FromSubfield2 uniqueInstance;

  private SchemaFromInd1OrIf7FromSubfield2() {}

  public static SchemaFromInd1OrIf7FromSubfield2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd1OrIf7FromSubfield2();
    return uniqueInstance;
  }
}
