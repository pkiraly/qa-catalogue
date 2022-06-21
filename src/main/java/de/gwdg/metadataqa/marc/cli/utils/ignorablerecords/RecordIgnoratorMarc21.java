package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.io.Serializable;

public class RecordIgnoratorMarc21 extends Marc21Filter implements RecordIgnorator, Serializable {

  private static final long serialVersionUID = 7969482654024463443L;

  public RecordIgnoratorMarc21(String ignorableRecordsInput) {
    parseInput(ignorableRecordsInput);
  }

  @Override
  public boolean isEmpty() {
    return super.isEmpty();
  }

  @Override
  public boolean isIgnorable(MarcRecord marcRecord) {
    if (isEmpty())
      return false;

    return met(marcRecord);
  }

  @Override
  public String toString() {
    return isEmpty() ? "" : conditions.toString();
  }
}
