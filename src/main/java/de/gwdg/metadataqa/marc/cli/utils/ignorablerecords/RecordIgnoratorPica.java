package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.io.Serializable;

public class RecordIgnoratorPica extends PicaFilter implements RecordIgnorator, Serializable {

  private static final long serialVersionUID = -6572155080023379764L;

  public RecordIgnoratorPica(String ignorableRecordsInput) {
    parse(ignorableRecordsInput);
  }

  @Override
  public boolean isEmpty() {
    return criteria.isEmpty();
  }

  @Override
  public boolean isIgnorable(MarcRecord marcRecord) {
    for (CriteriumPica criterium : criteria) {
      boolean passed = criterium.met(marcRecord);
      if (passed)
        return passed;
    }
    return false;
  }
}
