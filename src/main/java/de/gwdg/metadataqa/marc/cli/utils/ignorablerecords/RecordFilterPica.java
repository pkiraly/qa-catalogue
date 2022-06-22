package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.io.Serializable;

public class RecordFilterPica extends PicaFilter implements RecordFilter, Serializable {
  private static final long serialVersionUID = -3978064715895586316L;

  public RecordFilterPica(String allowableRecordsInput) {
    parse(allowableRecordsInput);
  }

  @Override
  public boolean isEmpty() {
    return getBooleanCriteria() == null;
  }

  @Override
  public boolean isAllowable(MarcRecord marcRecord) {
    if (isEmpty())
      return true;

    return metCriteria(marcRecord, booleanCriteria);
  }
}
