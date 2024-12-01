package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;

public class SchemaFromInd2 extends SchemaIndicatorExtractor {

  @Override
  protected String getSchemaAbbreviation(String indicatorSchemaCode, DataField dataField) {
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

  private static SchemaFromInd2 uniqueInstance;

  private SchemaFromInd2() {}

  public static SchemaFromInd2 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new SchemaFromInd2();
    return uniqueInstance;
  }
}
