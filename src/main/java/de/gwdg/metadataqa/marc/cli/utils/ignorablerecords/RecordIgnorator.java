package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

public interface RecordIgnorator {
  boolean isEmpty();
  boolean isIgnorable(MarcRecord marcRecord);
}
