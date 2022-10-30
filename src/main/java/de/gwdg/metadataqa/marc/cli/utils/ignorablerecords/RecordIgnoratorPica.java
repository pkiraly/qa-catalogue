package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.io.Serializable;

public class RecordIgnoratorPica extends PicaFilter implements RecordIgnorator, Serializable {

  private static final long serialVersionUID = -6572155080023379764L;

  public RecordIgnoratorPica(String ignorableRecordsInput) {
    parse(ignorableRecordsInput);
  }

  @Override
  public boolean isEmpty() {
    return getBooleanCriteria() == null;
  }

  @Override
  public boolean isIgnorable(BibliographicRecord marcRecord) {
    if (isEmpty())
      return false;

    return metCriteria(marcRecord, booleanCriteria);
  }
}
